package com.example.moviebooking;

import com.example.moviebooking.enums.SeatType;
import com.example.moviebooking.model.*;
import com.example.moviebooking.pricing.*;
import com.example.moviebooking.repo.*;
import com.example.moviebooking.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // --- Repos ---
        TheatreRepo theatreRepo = new InMemoryTheatreRepo();
        ShowRepo showRepo = new InMemoryShowRepo();
        BookingRepo bookingRepo = new InMemoryBookingRepo();

        // --- Pricing rules (strategy chain) ---
        List<PricingRule> pricingRules = List.of(
                new BasePricingRule(),
                new DemandPricingRule(0.7, 15),      // 15% surcharge when >70% booked
                new PeakHourPricingRule(18, 21, 10)   // 10% surcharge for 6-9 PM shows
        );
        PricingService pricingService = new PricingService(pricingRules);

        // --- Services ---
        SearchService searchService = new SearchService(theatreRepo, showRepo);
        BookingService bookingService = new BookingService(showRepo, bookingRepo, pricingService);
        CancellationService cancellationService = new CancellationService(bookingRepo);
        ShowManagementService showManagement = new ShowManagementService(showRepo);

        // ========== DATA SETUP ==========

        // Seats for Screen 1 (6 seats)
        List<Seat> screen1Seats = new ArrayList<>();
        screen1Seats.add(new Seat("S1", "A", 1, SeatType.SILVER));
        screen1Seats.add(new Seat("S2", "A", 2, SeatType.SILVER));
        screen1Seats.add(new Seat("S3", "A", 3, SeatType.SILVER));
        screen1Seats.add(new Seat("S4", "B", 1, SeatType.GOLD));
        screen1Seats.add(new Seat("S5", "B", 2, SeatType.GOLD));
        screen1Seats.add(new Seat("S6", "C", 1, SeatType.PLATINUM));

        // Seats for Screen 2 (4 seats)
        List<Seat> screen2Seats = new ArrayList<>();
        screen2Seats.add(new Seat("S7", "A", 1, SeatType.SILVER));
        screen2Seats.add(new Seat("S8", "A", 2, SeatType.SILVER));
        screen2Seats.add(new Seat("S9", "B", 1, SeatType.GOLD));
        screen2Seats.add(new Seat("S10", "B", 2, SeatType.GOLD));

        // Screens
        Screen screen1 = new Screen("SCR-1", "Screen 1", screen1Seats);
        Screen screen2 = new Screen("SCR-2", "Screen 2", screen2Seats);
        Screen screen3 = new Screen("SCR-3", "Audi 1", screen1Seats);

        // Theatres
        Theatre pvr = new Theatre("TH-1", "PVR Cinemas", "Bangalore", List.of(screen1, screen2));
        Theatre inox = new Theatre("TH-2", "INOX", "Bangalore", List.of(screen3));
        theatreRepo.save(pvr);
        theatreRepo.save(inox);

        // Movies
        Movie inception = new Movie("MOV-1", "Inception", 148);
        Movie interstellar = new Movie("MOV-2", "Interstellar", 169);

        // Base prices per seat type
        Map<SeatType, Double> prices = Map.of(
                SeatType.SILVER, 150.0,
                SeatType.GOLD, 250.0,
                SeatType.PLATINUM, 400.0
        );

        // ========== ADMIN: ADD SHOWS ==========

        Show show1 = showManagement.addShow(inception, screen1, pvr,
                LocalDateTime.of(2026, 3, 30, 10, 0),
                LocalDateTime.of(2026, 3, 30, 12, 30), prices);
        System.out.println("Show added: " + show1);

        Show show2 = showManagement.addShow(interstellar, screen2, pvr,
                LocalDateTime.of(2026, 3, 30, 19, 0),
                LocalDateTime.of(2026, 3, 30, 22, 0), prices);
        System.out.println("Show added: " + show2);

        Show show3 = showManagement.addShow(inception, screen3, inox,
                LocalDateTime.of(2026, 3, 30, 14, 0),
                LocalDateTime.of(2026, 3, 30, 16, 30), prices);
        System.out.println("Show added: " + show3);

        // ========== FLOW 1: City -> Movies -> Theatres -> Book ==========

        System.out.println("\n=== Movies in Bangalore ===");
        List<Movie> movies = searchService.getMoviesByCity("Bangalore");
        movies.forEach(System.out::println);

        System.out.println("\n=== Theatres showing Inception in Bangalore ===");
        List<Theatre> theatresForInception = searchService.getTheatresByMovieAndCity("MOV-1", "Bangalore");
        theatresForInception.forEach(System.out::println);

        System.out.println("\n=== Shows for Inception at PVR ===");
        List<Show> shows = searchService.getShows("MOV-1", "TH-1");
        shows.forEach(System.out::println);

        // ========== FLOW 2: City -> Theatres -> Movies -> Book ==========

        System.out.println("\n=== Theatres in Bangalore ===");
        searchService.getTheatresByCity("Bangalore").forEach(System.out::println);

        System.out.println("\n=== Movies at PVR Cinemas ===");
        searchService.getMoviesByTheatre("TH-1").forEach(System.out::println);

        // ========== BOOKING ==========

        System.out.println("\n=== Available seats for Inception at PVR ===");
        Show selectedShow = shows.get(0);
        searchService.getAvailableSeats(selectedShow).forEach(System.out::println);

        System.out.println("\n=== Booking 2 GOLD seats for Inception (morning show) ===");
        MovieTicket ticket1 = bookingService.bookTickets(selectedShow.getId(), List.of("S4", "S5"));
        System.out.println(ticket1);

        System.out.println("\n=== Booking 2 GOLD seats for Interstellar (peak hour 7 PM) ===");
        MovieTicket ticket2 = bookingService.bookTickets(show2.getId(), List.of("S9", "S10"));
        System.out.println(ticket2);

        // ========== CANCELLATION WITH REFUND ==========

        System.out.println("\n=== Cancelling first ticket ===");
        Refund refund = cancellationService.cancelTicket(ticket1.getTicketId());
        System.out.println(refund);
        System.out.println("Ticket status after cancel: " + ticket1.getStatus());

        System.out.println("\n=== Seats available again after cancellation ===");
        searchService.getAvailableSeats(selectedShow).forEach(System.out::println);

        // ========== CONCURRENCY: SHOW CONFLICT ==========

        System.out.println("\n=== Admin: Attempting overlapping show on Screen 1 ===");
        try {
            showManagement.addShow(interstellar, screen1, pvr,
                    LocalDateTime.of(2026, 3, 30, 11, 0),
                    LocalDateTime.of(2026, 3, 30, 14, 0), prices);
        } catch (IllegalStateException e) {
            System.out.println("Conflict detected: " + e.getMessage());
        }

        // ========== CONCURRENCY: DOUBLE BOOKING ==========

        System.out.println("\n=== Attempting double booking on same seats ===");
        try {
            bookingService.bookTickets(show2.getId(), List.of("S9"));
        } catch (IllegalStateException e) {
            System.out.println("Double booking prevented: " + e.getMessage());
        }
    }
}
