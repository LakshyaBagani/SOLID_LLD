package com.example.moviebooking.pricing;

import com.example.moviebooking.model.Seat;
import com.example.moviebooking.model.Show;

public interface PricingRule {
    double compute(Show show, Seat seat, double currentPrice);
}
