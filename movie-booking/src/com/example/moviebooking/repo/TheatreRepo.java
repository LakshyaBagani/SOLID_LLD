package com.example.moviebooking.repo;

import com.example.moviebooking.model.Theatre;

import java.util.List;
import java.util.Optional;

public interface TheatreRepo {
    void save(Theatre theatre);
    Optional<Theatre> getById(String theatreId);
    List<Theatre> getByCity(String city);
}
