package com.example.moviebooking.service;

import com.example.moviebooking.enums.SeatType;
import com.example.moviebooking.model.Movie;
import com.example.moviebooking.model.Screen;
import com.example.moviebooking.model.Show;
import com.example.moviebooking.model.Theatre;
import com.example.moviebooking.repo.ShowRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ShowManagementService {
    private final ShowRepo showRepo;
    private final Map<String, ReentrantLock> screenLocks = new ConcurrentHashMap<>();

    public ShowManagementService(ShowRepo showRepo) {
        this.showRepo = showRepo;
    }

    public Show addShow(Movie movie, Screen screen, Theatre theatre,
                        LocalDateTime startTime, LocalDateTime endTime,
                        Map<SeatType, Double> basePriceMap) {
        ReentrantLock lock = screenLocks.computeIfAbsent(screen.getId(), k -> new ReentrantLock());
        lock.lock();
        try {
            List<Show> existingShows = showRepo.getByScreenId(screen.getId());
            for (Show existing : existingShows) {
                if (timesOverlap(startTime, endTime, existing.getStartTime(), existing.getEndTime())) {
                    throw new IllegalStateException(
                            "Time conflict with existing show: " + existing);
                }
            }

            String showId = "SHW-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            Show show = new Show(showId, movie, screen, theatre, startTime, endTime, basePriceMap);
            showRepo.save(show);
            return show;
        } finally {
            lock.unlock();
        }
    }

    private boolean timesOverlap(LocalDateTime s1, LocalDateTime e1,
                                  LocalDateTime s2, LocalDateTime e2) {
        return s1.isBefore(e2) && s2.isBefore(e1);
    }
}
