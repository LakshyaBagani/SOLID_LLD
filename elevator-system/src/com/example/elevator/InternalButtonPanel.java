package com.example.elevator;

import java.util.Set;

public class InternalButtonPanel {
    private final Elevator elevator;

    public InternalButtonPanel(Elevator elevator) {
        this.elevator = elevator;
    }

    public void pressFloor(int floor, Set<Integer> maintenanceFloors) {
        System.out.println("[Internal Panel] Elevator " + elevator.getId() + ": Floor " + floor + " pressed");
        elevator.addRequest(floor, maintenanceFloors);
    }

    public void pressOpen() {
        System.out.println("[Internal Panel] Elevator " + elevator.getId() + ": Open pressed");
        elevator.openDoor();
    }

    public void pressClose() {
        System.out.println("[Internal Panel] Elevator " + elevator.getId() + ": Close pressed");
        elevator.closeDoor();
    }

    public void pressEmergency() {
        System.out.println("[Internal Panel] Elevator " + elevator.getId() + ": EMERGENCY pressed");
        elevator.triggerEmergency();
    }

    public void pressAlarm() {
        System.out.println("[Internal Panel] Elevator " + elevator.getId() + ": ALARM pressed");
        elevator.triggerAlarm();
    }
}
