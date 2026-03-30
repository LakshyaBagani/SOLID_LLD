package com.example.elevator;

import java.util.*;

public class Elevator {
    private final int id;
    private final double weightLimit;
    private int currentFloor;
    private ElevatorState state;
    private DoorState doorState;
    private double currentWeight;
    private final TreeSet<Integer> upRequests;
    private final TreeSet<Integer> downRequests;

    public Elevator(int id, double weightLimit) {
        this.id = id;
        this.weightLimit = weightLimit;
        this.currentFloor = 0;
        this.state = ElevatorState.IDLE;
        this.doorState = DoorState.CLOSED;
        this.currentWeight = 0;
        this.upRequests = new TreeSet<>();
        this.downRequests = new TreeSet<>(Collections.reverseOrder());
    }

    public int getId() { return id; }
    public double getWeightLimit() { return weightLimit; }
    public int getCurrentFloor() { return currentFloor; }
    public ElevatorState getState() { return state; }
    public DoorState getDoorState() { return doorState; }
    public double getCurrentWeight() { return currentWeight; }

    public void setState(ElevatorState state) { this.state = state; }

    public void addRequest(int floor, Set<Integer> maintenanceFloors) {
        if (maintenanceFloors.contains(floor)) {
            System.out.println("  [Elevator " + id + "] Floor " + floor + " is under maintenance. Request ignored.");
            return;
        }
        if (floor > currentFloor) {
            upRequests.add(floor);
        } else if (floor < currentFloor) {
            downRequests.add(floor);
        }
    }

    public boolean hasRequests() {
        return !upRequests.isEmpty() || !downRequests.isEmpty();
    }

    public void processRequests(Set<Integer> maintenanceFloors) {
        if (state == ElevatorState.MAINTENANCE) {
            System.out.println("  [Elevator " + id + "] Under maintenance. Cannot move.");
            return;
        }

        while (hasRequests()) {
            processUpRequests(maintenanceFloors);
            processDownRequests(maintenanceFloors);
        }
        state = ElevatorState.IDLE;
        System.out.println("  [Elevator " + id + "] Now IDLE at floor " + currentFloor);
    }

    private void processUpRequests(Set<Integer> maintenanceFloors) {
        state = ElevatorState.UP;
        while (!upRequests.isEmpty()) {
            int next = upRequests.pollFirst();
            if (maintenanceFloors.contains(next)) {
                System.out.println("  [Elevator " + id + "] Skipping floor " + next + " (maintenance)");
                continue;
            }
            moveTo(next);
        }
    }

    private void processDownRequests(Set<Integer> maintenanceFloors) {
        state = ElevatorState.DOWN;
        while (!downRequests.isEmpty()) {
            int next = downRequests.pollFirst();
            if (maintenanceFloors.contains(next)) {
                System.out.println("  [Elevator " + id + "] Skipping floor " + next + " (maintenance)");
                continue;
            }
            moveTo(next);
        }
    }

    private void moveTo(int floor) {
        System.out.println("  [Elevator " + id + "] Moving " + (floor > currentFloor ? "UP" : "DOWN")
                + " from floor " + currentFloor + " to floor " + floor);
        currentFloor = floor;
        openDoor();
        closeDoor();
    }

    public void openDoor() {
        doorState = DoorState.OPEN;
        System.out.println("  [Elevator " + id + "] Door OPENED at floor " + currentFloor);
    }

    public void closeDoor() {
        doorState = DoorState.CLOSED;
        System.out.println("  [Elevator " + id + "] Door CLOSED");
    }

    public void updateWeight(double weight) {
        this.currentWeight = weight;
        if (currentWeight > weightLimit) {
            System.out.println("  [Elevator " + id + "] OVERWEIGHT! Limit: " + weightLimit
                    + "kg, Current: " + currentWeight + "kg");
            triggerOverweightAlarm();
        }
    }

    private void triggerOverweightAlarm() {
        state = ElevatorState.IDLE;
        openDoor();
        System.out.println("  [Elevator " + id + "] ALARM: Please reduce weight before doors close.");
    }

    public void triggerEmergency() {
        state = ElevatorState.IDLE;
        upRequests.clear();
        downRequests.clear();
        openDoor();
        System.out.println("  [Elevator " + id + "] EMERGENCY STOP at floor " + currentFloor);
    }

    public void triggerAlarm() {
        System.out.println("  [Elevator " + id + "] ALARM RINGING at floor " + currentFloor);
        state = ElevatorState.IDLE;
        upRequests.clear();
        downRequests.clear();
    }

    @Override
    public String toString() {
        return "Elevator " + id + "(floor=" + currentFloor + ", state=" + state
                + ", weight=" + currentWeight + "/" + weightLimit + "kg)";
    }
}
