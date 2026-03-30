package com.example.moviebooking.service;

import com.example.moviebooking.model.MovieTicket;
import com.example.moviebooking.model.Seat;
import com.example.moviebooking.model.Show;
import com.example.moviebooking.repo.BookingRepo;
import com.example.moviebooking.repo.ShowRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingService {
    private final ShowRepo showRepo;
    private final BookingRepo bookingRepo;
    private final PricingService pricingService;

    public BookingService(ShowRepo showRepo, BookingRepo bookingRepo, PricingService pricingService) {
        this.showRepo = showRepo;
        this.bookingRepo = bookingRepo;
        this.pricingService = pricingService;
    }

    public MovieTicket bookTickets(String showId, List<String> seatIds) {
        Show show = showRepo.getById(showId)
                .orElseThrow(() -> new IllegalArgumentException("Show not found: " + showId));

        show.lock();
        try {
            if (!show.areSeatsAvailable(seatIds)) {
                throw new IllegalStateException("One or more seats are already booked");
            }

            List<Seat> seats = resolveSeats(show, seatIds);
            double totalPrice = pricingService.calculateTotalPrice(show, seats);

            show.bookSeats(seatIds);

            String ticketId = "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            MovieTicket ticket = new MovieTicket(ticketId, show, seats, totalPrice, LocalDateTime.now());
            bookingRepo.save(ticket);
            return ticket;
        } finally {
            show.unlock();
        }
    }

    private List<Seat> resolveSeats(Show show, List<String> seatIds) {
        Map<String, Seat> seatMap = show.getScreen().getSeats().stream()
                .collect(Collectors.toMap(Seat::getId, s -> s));
        List<Seat> seats = new ArrayList<>();
        for (String seatId : seatIds) {
            Seat seat = seatMap.get(seatId);
            if (seat == null) {
                throw new IllegalArgumentException("Invalid seat: " + seatId);
            }
            seats.add(seat);
        }
        return seats;
    }
}
