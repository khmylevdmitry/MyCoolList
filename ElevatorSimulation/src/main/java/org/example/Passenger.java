package org.example;

class Passenger {
    private int currentFloor;
    private int destinationFloor;

    public Passenger(int currentFloor, int destinationFloor) {
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}