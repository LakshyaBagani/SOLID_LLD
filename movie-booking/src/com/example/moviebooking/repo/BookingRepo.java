package com.example.moviebooking.repo;

import com.example.moviebooking.model.MovieTicket;

import java.util.Optional;

public interface BookingRepo {
    void save(MovieTicket ticket);
    Optional<MovieTicket> getById(String ticketId);
}
