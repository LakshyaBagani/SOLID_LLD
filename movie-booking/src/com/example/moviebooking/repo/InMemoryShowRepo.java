package com.example.moviebooking.repo;

import com.example.moviebooking.model.Show;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryShowRepo implements ShowRepo {
    private final Map<String, Show> shows = new HashMap<>();

    @Override
    public void save(Show show) {
        shows.put(show.getId(), show);
    }

    @Override
    public Optional<Show> getById(String showId) {
        return Optional.ofNullable(shows.get(showId));
    }

    @Override
    public List<Show> getByScreenId(String screenId) {
        return shows.values().stream()
                .filter(s -> s.getScreen().getId().equals(screenId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Show> getByTheatreId(String theatreId) {
        return shows.values().stream()
                .filter(s -> s.getTheatre().getId().equals(theatreId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Show> getByMovieIdAndCity(String movieId, String city) {
        return shows.values().stream()
                .filter(s -> s.getMovie().getId().equals(movieId) &&
                             s.getTheatre().getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    @Override
    public List<Show> getByMovieIdAndTheatreId(String movieId, String theatreId) {
        return shows.values().stream()
                .filter(s -> s.getMovie().getId().equals(movieId) &&
                             s.getTheatre().getId().equals(theatreId))
                .collect(Collectors.toList());
    }
}
