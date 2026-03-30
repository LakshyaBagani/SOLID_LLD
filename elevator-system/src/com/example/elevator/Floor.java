package com.example.elevator;

public class Floor {
    private final int floorNumber;
    private boolean underMaintenance;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.underMaintenance = false;
    }

    public int getFloorNumber() { return floorNumber; }
    public boolean isUnderMaintenance() { return underMaintenance; }
    public void setUnderMaintenance(boolean underMaintenance) { this.underMaintenance = underMaintenance; }

    @Override
    public String toString() {
        return "Floor " + floorNumber + (underMaintenance ? " [MAINTENANCE]" : "");
    }
}
