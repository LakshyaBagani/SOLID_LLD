package com.example.moviebooking.repo;

import com.example.moviebooking.model.Show;

import java.util.List;
import java.util.Optional;

public interface ShowRepo {
    void save(Show show);
    Optional<Show> getById(String showId);
    List<Show> getByScreenId(String screenId);
    List<Show> getByTheatreId(String theatreId);
    List<Show> getByMovieIdAndCity(String movieId, String city);
    List<Show> getByMovieIdAndTheatreId(String movieId, String theatreId);
}
