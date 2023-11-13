package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private Building building;

    public Generator() {
        Random rand = new Random();

        int numOfFloors = rand.nextInt(16) + 5;
        building = new Building(numOfFloors);

        for (int i = 0; i < numOfFloors; i++) {
            int numPassengers = rand.nextInt(11);
            List<Integer> destinations = new ArrayList<>();
            for (int j = 0; j < numPassengers; j++) {
                int destination;
                do {
                    destination = rand.nextInt(numOfFloors)+1;
                } while (destination == i+1);
                destinations.add(destination);
            }
            building.addPassengersToFloor(i, numPassengers, destinations);
        }
    }

    public void start() {
        building.startElevator();
    }

}
