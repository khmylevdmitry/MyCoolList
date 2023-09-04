package org.example;

import java.util.ArrayList;
import java.util.List;

class Floor {
    List<Passenger> passengers = new ArrayList<>();

    public Floor() {
        passengers = new ArrayList<>();
    }

    public void addPassengers(int numPassengers, List<Integer> destinationFloors) {
        for (int i = 0; i < numPassengers; i++) {
            passengers.add(new Passenger(destinationFloors.get(i)));
        }
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public boolean isEmpty() {
        return passengers.isEmpty();
    }

    public boolean hasPassengersGoingUp(int currentFloor) {
        for (Passenger passenger : passengers) {
            if (passenger.getDestinationFloor() > currentFloor) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPassengersGoingDown(int currentFloor) {
        for (Passenger passenger : passengers) {
            if (passenger.getDestinationFloor() < currentFloor) {
                return true;
            }
        }
        return false;
    }

}

