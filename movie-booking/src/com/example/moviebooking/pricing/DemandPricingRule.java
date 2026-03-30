package com.example.moviebooking.pricing;

import com.example.moviebooking.model.Seat;
import com.example.moviebooking.model.Show;

public class DemandPricingRule implements PricingRule {
    private final double highDemandThreshold;
    private final double surchargePercent;

    public DemandPricingRule(double highDemandThreshold, double surchargePercent) {
        this.highDemandThreshold = highDemandThreshold;
        this.surchargePercent = surchargePercent;
    }

    @Override
    public double compute(Show show, Seat seat, double currentPrice) {
        double occupancy = (double) show.getBookedSeatCount() / show.getTotalSeatCount();
        if (occupancy >= highDemandThreshold) {
            return currentPrice + (currentPrice * surchargePercent / 100);
        }
        return currentPrice;
    }
}
