package com.example.moviebooking.repo;

import com.example.moviebooking.model.Theatre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryTheatreRepo implements TheatreRepo {
    private final Map<String, Theatre> theatres = new HashMap<>();

    @Override
    public void save(Theatre theatre) {
        theatres.put(theatre.getId(), theatre);
    }

    @Override
    public Optional<Theatre> getById(String theatreId) {
        return Optional.ofNullable(theatres.get(theatreId));
    }

    @Override
    public List<Theatre> getByCity(String city) {
        return theatres.values().stream()
                .filter(t -> t.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }
}
