package com.example.elevator;

import java.util.*;

public class Building {
    private final List<Floor> floors;
    private final List<Elevator> elevators;
    private final Map<Integer, ExternalButtonPanel> externalPanels;
    private final Map<Integer, InternalButtonPanel> internalPanels;
    private final ElevatorController controller;

    public Building(int numFloors, List<Elevator> elevators, ElevatorSelectionStrategy strategy) {
        this.floors = new ArrayList<>();
        for (int i = 0; i < numFloors; i++) {
            floors.add(new Floor(i));
        }
        this.elevators = elevators;
        this.controller = new ElevatorController(elevators, strategy);

        this.externalPanels = new HashMap<>();
        for (Floor floor : floors) {
            externalPanels.put(floor.getFloorNumber(), new ExternalButtonPanel(floor.getFloorNumber()));
        }

        this.internalPanels = new HashMap<>();
        for (Elevator elevator : elevators) {
            internalPanels.put(elevator.getId(), new InternalButtonPanel(elevator));
        }
    }

    public ExternalButtonPanel getExternalPanel(int floor) { return externalPanels.get(floor); }
    public InternalButtonPanel getInternalPanel(int elevatorId) { return internalPanels.get(elevatorId); }
    public ElevatorController getController() { return controller; }
    public List<Floor> getFloors() { return floors; }
    public List<Elevator> getElevators() { return elevators; }
}
