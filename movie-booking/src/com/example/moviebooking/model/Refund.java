package com.example.moviebooking.model;

import java.time.LocalDateTime;

public class Refund {
    private final String refundId;
    private final String ticketId;
    private final double amount;
    private final LocalDateTime refundTime;

    public Refund(String refundId, String ticketId, double amount, LocalDateTime refundTime) {
        this.refundId = refundId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.refundTime = refundTime;
    }

    public String getRefundId() { return refundId; }
    public String getTicketId() { return ticketId; }
    public double getAmount() { return amount; }
    public LocalDateTime getRefundTime() { return refundTime; }

    @Override
    public String toString() {
        return "Refund[" + refundId + "] for Ticket[" + ticketId + "] Rs." + amount;
    }
}
