package com.example.moviebooking.pricing;

import com.example.moviebooking.model.Seat;
import com.example.moviebooking.model.Show;

public class PeakHourPricingRule implements PricingRule {
    private final int peakStartHour;
    private final int peakEndHour;
    private final double surchargePercent;

    public PeakHourPricingRule(int peakStartHour, int peakEndHour, double surchargePercent) {
        this.peakStartHour = peakStartHour;
        this.peakEndHour = peakEndHour;
        this.surchargePercent = surchargePercent;
    }

    @Override
    public double compute(Show show, Seat seat, double currentPrice) {
        int showHour = show.getStartTime().getHour();
        if (showHour >= peakStartHour && showHour <= peakEndHour) {
            return currentPrice + (currentPrice * surchargePercent / 100);
        }
        return currentPrice;
    }
}
