package com.example.elevator;

public class ExternalButtonPanel {
    private final int floor;

    public ExternalButtonPanel(int floor) {
        this.floor = floor;
    }

    public Request pressUp() {
        System.out.println("[External Panel] Floor " + floor + ": UP pressed");
        return new Request(floor, Direction.UP);
    }

    public Request pressDown() {
        System.out.println("[External Panel] Floor " + floor + ": DOWN pressed");
        return new Request(floor, Direction.DOWN);
    }

    public int getFloor() { return floor; }
}
