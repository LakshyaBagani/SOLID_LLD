package com.example.moviebooking.model;

import com.example.moviebooking.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public class MovieTicket {
    private final String ticketId;
    private final Show show;
    private final List<Seat> bookedSeats;
    private final double totalPrice;
    private final LocalDateTime bookingTime;
    private BookingStatus status;

    public MovieTicket(String ticketId, Show show, List<Seat> bookedSeats,
                       double totalPrice, LocalDateTime bookingTime) {
        this.ticketId = ticketId;
        this.show = show;
        this.bookedSeats = List.copyOf(bookedSeats);
        this.totalPrice = totalPrice;
        this.bookingTime = bookingTime;
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() { this.status = BookingStatus.CANCELLED; }

    public String getTicketId() { return ticketId; }
    public Show getShow() { return show; }
    public List<Seat> getBookedSeats() { return bookedSeats; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public BookingStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "Ticket[" + ticketId + "] " + show.getMovie().getTitle() +
               " | " + show.getTheatre().getName() +
               " | Seats: " + bookedSeats +
               " | Rs." + totalPrice +
               " | " + status;
    }
}
