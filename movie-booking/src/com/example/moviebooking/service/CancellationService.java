package com.example.moviebooking.service;

import com.example.moviebooking.enums.BookingStatus;
import com.example.moviebooking.model.MovieTicket;
import com.example.moviebooking.model.Refund;
import com.example.moviebooking.model.Seat;
import com.example.moviebooking.model.Show;
import com.example.moviebooking.repo.BookingRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CancellationService {
    private final BookingRepo bookingRepo;

    public CancellationService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public Refund cancelTicket(String ticketId) {
        MovieTicket ticket = bookingRepo.getById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found: " + ticketId));

        if (ticket.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Ticket already cancelled: " + ticketId);
        }

        Show show = ticket.getShow();
        show.lock();
        try {
            List<String> seatIds = ticket.getBookedSeats().stream()
                    .map(Seat::getId)
                    .collect(Collectors.toList());
            show.releaseSeats(seatIds);
            ticket.cancel();
        } finally {
            show.unlock();
        }

        double refundAmount = ticket.getTotalPrice();
        String refundId = "RFD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new Refund(refundId, ticketId, refundAmount, LocalDateTime.now());
    }
}
