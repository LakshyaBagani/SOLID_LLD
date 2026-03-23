package com.example.parking;

public interface PricingPolicy {
    int ratePerHour(SlotType slotType);
}
