package com.example.pen;

public class App {
    public static void main(String[] args) {
        // Cap pen with blue refill
        System.out.println("=== Cap Pen ===");
        Pen capPen = new Pen(new CapStrategy(), new Refill(Color.BLUE));
        capPen.write("Hello");

        // Change refill to red
        capPen.changeRefill(new Refill(Color.RED));
        capPen.write("World");

        // Click pen with black refill
        System.out.println("\n=== Click Pen ===");
        Pen clickPen = new Pen(new ClickStrategy(), new Refill(Color.BLACK));
        clickPen.write("Design Patterns");

        // Change refill to green
        clickPen.changeRefill(new Refill(Color.GREEN));
        clickPen.write("are fun");
    }
}
