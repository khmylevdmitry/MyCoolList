package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Building {
    private List<List<Passenger>> floors;
    private Random random = new Random();

    public Building(int numberOfFloors) {
        floors = new ArrayList<>(numberOfFloors);
        for (int i = 0; i < numberOfFloors; i++) {
            floors.add(generatePassengersOnFloor());
        }
    }

    public List<Passenger> getPassengersOnFloor(int floor) {
        return floors.get(floor);
    }

    public void addPassengerToFloor(int floor, Passenger passenger) {
        floors.get(floor).add(passenger);
    }

    public void removePassengerFromFloor(int floor, Passenger passenger) {
        floors.get(floor).remove(passenger);
    }

    private List<Passenger> generatePassengersOnFloor() {
        List<Passenger> passengers = new ArrayList<>();
        int passengerCount = random.nextInt(11); // 0 to 10 passengers
        for (int j = 0; j < passengerCount; j++) {
            int destFloor = generateRandomFloor(0, floors.size() - 1, j);
            passengers.add(new Passenger(j, destFloor));
        }
        return passengers;
    }

    private int generateRandomFloor(int minFloor, int maxFloor, int currentFloor) {
        if (minFloor >= maxFloor) {
            throw new IllegalArgumentException("Invalid floor range");
        }

        if (maxFloor - minFloor <= 1) {
            return currentFloor; // No other floor to choose from
        }

        int newFloor = random.nextInt(maxFloor - minFloor) + minFloor;
        while (newFloor == currentFloor) {
            newFloor = random.nextInt(maxFloor - minFloor) + minFloor;
        }
        return newFloor;
    }
}