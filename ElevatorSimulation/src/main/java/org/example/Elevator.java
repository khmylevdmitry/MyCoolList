package org.example;

import java.util.ArrayList;
import java.util.List;

class Elevator {
    private static final int MAX_CAPACITY = 5;
    private int currentFloor;
    private int maxDestinationFloor;
    private List<Passenger> passengers;

    public Elevator(int startingFloor) {
        currentFloor = startingFloor;
        maxDestinationFloor = startingFloor;
        passengers = new ArrayList<>();
    }

    public void move() {
        if (currentFloor < maxDestinationFloor) {
            currentFloor++;
        } else if (currentFloor > maxDestinationFloor) {
            currentFloor--;
        }
        System.out.println("Elevator is on floor " + currentFloor);
    }

    public void boardPassenger(Passenger passenger) {
        passengers.add(passenger);
        if (passenger.getDestinationFloor() > maxDestinationFloor) {
            maxDestinationFloor = passenger.getDestinationFloor();
        }
    }

    public boolean isFull() {
        return passengers.size() >= MAX_CAPACITY;
    }

    public List<Passenger> unloadPassengers() {
        List<Passenger> unloadedPassengers = new ArrayList<>();
        for (Passenger passenger : passengers) {
            if (passenger.getDestinationFloor() == currentFloor) {
                unloadedPassengers.add(passenger);
            }
        }
        passengers.removeAll(unloadedPassengers);
        if (passengers.isEmpty()) {
            maxDestinationFloor = currentFloor;
        }
        return unloadedPassengers;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }
}
