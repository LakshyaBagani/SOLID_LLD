package com.example.moviebooking.pricing;

import com.example.moviebooking.model.Seat;
import com.example.moviebooking.model.Show;

public class BasePricingRule implements PricingRule {

    @Override
    public double compute(Show show, Seat seat, double currentPrice) {
        return show.getBasePrice(seat.getSeatType());
    }
}
