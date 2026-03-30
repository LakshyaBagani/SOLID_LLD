package com.example.moviebooking.service;

import com.example.moviebooking.model.Movie;
import com.example.moviebooking.model.Show;
import com.example.moviebooking.model.Seat;
import com.example.moviebooking.model.Theatre;
import com.example.moviebooking.repo.ShowRepo;
import com.example.moviebooking.repo.TheatreRepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchService {
    private final TheatreRepo theatreRepo;
    private final ShowRepo showRepo;

    public SearchService(TheatreRepo theatreRepo, ShowRepo showRepo) {
        this.theatreRepo = theatreRepo;
        this.showRepo = showRepo;
    }

    public List<Theatre> getTheatresByCity(String city) {
        return theatreRepo.getByCity(city);
    }

    public List<Movie> getMoviesByCity(String city) {
        List<Theatre> theatres = theatreRepo.getByCity(city);
        Set<String> seen = new HashSet<>();
        List<Movie> movies = new ArrayList<>();
        for (Theatre theatre : theatres) {
            List<Show> shows = showRepo.getByTheatreId(theatre.getId());
            for (Show show : shows) {
                if (seen.add(show.getMovie().getId())) {
                    movies.add(show.getMovie());
                }
            }
        }
        return movies;
    }

    public List<Movie> getMoviesByTheatre(String theatreId) {
        List<Show> shows = showRepo.getByTheatreId(theatreId);
        Set<String> seen = new HashSet<>();
        List<Movie> movies = new ArrayList<>();
        for (Show show : shows) {
            if (seen.add(show.getMovie().getId())) {
                movies.add(show.getMovie());
            }
        }
        return movies;
    }

    public List<Theatre> getTheatresByMovieAndCity(String movieId, String city) {
        List<Show> shows = showRepo.getByMovieIdAndCity(movieId, city);
        Set<String> seen = new HashSet<>();
        List<Theatre> theatres = new ArrayList<>();
        for (Show show : shows) {
            if (seen.add(show.getTheatre().getId())) {
                theatres.add(show.getTheatre());
            }
        }
        return theatres;
    }

    public List<Show> getShows(String movieId, String theatreId) {
        return showRepo.getByMovieIdAndTheatreId(movieId, theatreId);
    }

    public List<Seat> getAvailableSeats(Show show) {
        return show.getScreen().getSeats().stream()
                .filter(seat -> show.areSeatsAvailable(List.of(seat.getId())))
                .collect(Collectors.toList());
    }
}
