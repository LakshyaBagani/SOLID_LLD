package com.example.elevator;

import java.util.*;

public class App {
    public static void main(String[] args) {
        // 3 elevators with different weight limits
        Elevator e1 = new Elevator(1, 700);
        Elevator e2 = new Elevator(2, 500);
        Elevator e3 = new Elevator(3, 800);
        List<Elevator> elevators = List.of(e1, e2, e3);

        // 10-floor building
        Building building = new Building(10, elevators, new NearestElevatorStrategy());
        ElevatorController controller = building.getController();

        // --- External button: someone on floor 3 wants to go UP ---
        System.out.println("=== External Request: Floor 3 UP ===");
        Request req1 = building.getExternalPanel(3).pressUp();
        controller.handleExternalRequest(req1);

        // --- Internal button: passenger inside elevator 1 presses floor 7 ---
        System.out.println("\n=== Internal: Elevator 1 -> Floor 7 ===");
        building.getInternalPanel(1).pressFloor(7, controller.getMaintenanceFloors());
        controller.handleInternalRequest(e1, 7);

        // --- External button: someone on floor 5 wants to go DOWN ---
        System.out.println("\n=== External Request: Floor 5 DOWN ===");
        Request req2 = building.getExternalPanel(5).pressDown();
        controller.handleExternalRequest(req2);

        // --- Weight check: within limit ---
        System.out.println("\n=== Weight Check: Elevator 2 (limit=500kg) ===");
        e2.updateWeight(450);
        System.out.println("  Status: " + e2);

        // --- Overweight triggers alarm and opens door ---
        System.out.println("\n=== Overweight Trigger: Elevator 2 ===");
        e2.updateWeight(550);
        System.out.println("  Status: " + e2);

        // --- Floor under maintenance: requests to that floor are ignored ---
        System.out.println("\n=== Floor 4 Under Maintenance ===");
        controller.addMaintenanceFloor(4);
        Request req3 = building.getExternalPanel(4).pressUp();
        controller.handleExternalRequest(req3);
        building.getInternalPanel(1).pressFloor(4, controller.getMaintenanceFloors());

        // --- Alarm button inside elevator 3 ---
        System.out.println("\n=== Alarm Pressed in Elevator 3 ===");
        building.getInternalPanel(3).pressAlarm();

        // --- Emergency stop in elevator 1 ---
        System.out.println("\n=== Emergency in Elevator 1 ===");
        building.getInternalPanel(1).pressEmergency();

        // --- Elevator 2 put under maintenance, won't be dispatched ---
        System.out.println("\n=== Elevator 2 Set to Maintenance ===");
        controller.setElevatorMaintenance(e2, true);
        Request req4 = building.getExternalPanel(2).pressUp();
        controller.handleExternalRequest(req4);

        // --- Open/Close door manually ---
        System.out.println("\n=== Manual Door Control: Elevator 3 ===");
        building.getInternalPanel(3).pressOpen();
        building.getInternalPanel(3).pressClose();
    }
}
