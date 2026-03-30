package com.example.elevator;

import java.util.*;

public class ElevatorController {
    private final List<Elevator> elevators;
    private final ElevatorSelectionStrategy strategy;
    private final Set<Integer> maintenanceFloors;

    public ElevatorController(List<Elevator> elevators, ElevatorSelectionStrategy strategy) {
        this.elevators = elevators;
        this.strategy = strategy;
        this.maintenanceFloors = new HashSet<>();
    }

    public void handleExternalRequest(Request request) {
        if (maintenanceFloors.contains(request.getTargetFloor())) {
            System.out.println("[Controller] Floor " + request.getTargetFloor()
                    + " is under maintenance. Request ignored.");
            return;
        }

        Optional<Elevator> selected = strategy.selectElevator(
                elevators, request.getTargetFloor(), request.getDirection());

        if (selected.isPresent()) {
            Elevator elevator = selected.get();
            System.out.println("[Controller] Dispatching Elevator " + elevator.getId() + " for " + request);
            elevator.addRequest(request.getTargetFloor(), maintenanceFloors);
            elevator.processRequests(maintenanceFloors);
        } else {
            System.out.println("[Controller] No elevator available for " + request);
        }
    }

    public void handleInternalRequest(Elevator elevator, int targetFloor) {
        elevator.addRequest(targetFloor, maintenanceFloors);
        elevator.processRequests(maintenanceFloors);
    }

    public void addMaintenanceFloor(int floor) {
        maintenanceFloors.add(floor);
        System.out.println("[Controller] Floor " + floor + " added to maintenance");
    }

    public void removeMaintenanceFloor(int floor) {
        maintenanceFloors.remove(floor);
        System.out.println("[Controller] Floor " + floor + " removed from maintenance");
    }

    public void setElevatorMaintenance(Elevator elevator, boolean maintenance) {
        if (maintenance) {
            elevator.setState(ElevatorState.MAINTENANCE);
            System.out.println("[Controller] Elevator " + elevator.getId() + " set to MAINTENANCE");
        } else {
            elevator.setState(ElevatorState.IDLE);
            System.out.println("[Controller] Elevator " + elevator.getId() + " back to service");
        }
    }

    public Set<Integer> getMaintenanceFloors() { return maintenanceFloors; }
    public List<Elevator> getElevators() { return elevators; }
}
