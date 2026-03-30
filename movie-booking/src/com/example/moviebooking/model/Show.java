package com.example.moviebooking.model;

import com.example.moviebooking.enums.SeatType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Show {
    private final String id;
    private final Movie movie;
    private final Screen screen;
    private final Theatre theatre;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Map<SeatType, Double> basePriceMap;
    private final Set<String> bookedSeatIds = new HashSet<>();
    private final ReentrantLock lock = new ReentrantLock();

    public Show(String id, Movie movie, Screen screen, Theatre theatre,
                LocalDateTime startTime, LocalDateTime endTime,
                Map<SeatType, Double> basePriceMap) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.theatre = theatre;
        this.startTime = startTime;
        this.endTime = endTime;
        this.basePriceMap = Map.copyOf(basePriceMap);
    }

    public void lock() { lock.lock(); }
    public void unlock() { lock.unlock(); }

    public boolean areSeatsAvailable(List<String> seatIds) {
        for (String seatId : seatIds) {
            if (bookedSeatIds.contains(seatId)) return false;
        }
        return true;
    }

    public void bookSeats(List<String> seatIds) {
        bookedSeatIds.addAll(seatIds);
    }

    public void releaseSeats(List<String> seatIds) {
        bookedSeatIds.removeAll(new HashSet<>(seatIds));
    }

    public int getBookedSeatCount() { return bookedSeatIds.size(); }
    public int getTotalSeatCount() { return screen.getSeats().size(); }

    public double getBasePrice(SeatType seatType) {
        return basePriceMap.getOrDefault(seatType, 0.0);
    }

    public String getId() { return id; }
    public Movie getMovie() { return movie; }
    public Screen getScreen() { return screen; }
    public Theatre getTheatre() { return theatre; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public Map<SeatType, Double> getBasePriceMap() { return basePriceMap; }

    @Override
    public String toString() {
        return movie.getTitle() + " at " + theatre.getName() +
               " [" + screen.getName() + "] " + startTime;
    }
}
