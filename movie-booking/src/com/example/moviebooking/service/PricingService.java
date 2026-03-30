package com.example.moviebooking.service;

import com.example.moviebooking.model.Seat;
import com.example.moviebooking.model.Show;
import com.example.moviebooking.pricing.PricingRule;

import java.util.List;

public class PricingService {
    private final List<PricingRule> rules;

    public PricingService(List<PricingRule> rules) {
        this.rules = rules;
    }

    public double calculatePrice(Show show, Seat seat) {
        double price = 0;
        for (PricingRule rule : rules) {
            price = rule.compute(show, seat, price);
        }
        return price;
    }

    public double calculateTotalPrice(Show show, List<Seat> seats) {
        double total = 0;
        for (Seat seat : seats) {
            total += calculatePrice(show, seat);
        }
        return total;
    }
}
