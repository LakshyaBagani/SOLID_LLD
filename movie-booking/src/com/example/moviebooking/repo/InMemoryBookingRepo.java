package com.example.moviebooking.repo;

import com.example.moviebooking.model.MovieTicket;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryBookingRepo implements BookingRepo {
    private final Map<String, MovieTicket> tickets = new HashMap<>();

    @Override
    public void save(MovieTicket ticket) {
        tickets.put(ticket.getTicketId(), ticket);
    }

    @Override
    public Optional<MovieTicket> getById(String ticketId) {
        return Optional.ofNullable(tickets.get(ticketId));
    }
}
