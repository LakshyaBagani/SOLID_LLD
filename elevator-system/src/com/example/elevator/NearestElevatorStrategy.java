package com.example.elevator;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class NearestElevatorStrategy implements ElevatorSelectionStrategy {

    @Override
    public Optional<Elevator> selectElevator(List<Elevator> elevators, int requestedFloor, Direction direction) {
        return elevators.stream()
                .filter(e -> e.getState() != ElevatorState.MAINTENANCE)
                .filter(e -> e.getState() == ElevatorState.IDLE
                        || (direction == Direction.UP && e.getState() == ElevatorState.UP
                            && e.getCurrentFloor() <= requestedFloor)
                        || (direction == Direction.DOWN && e.getState() == ElevatorState.DOWN
                            && e.getCurrentFloor() >= requestedFloor))
                .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - requestedFloor)));
    }
}
