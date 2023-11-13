package org.example;

import java.util.List;
import java.util.Random;

public class ElevatorSimulation {
    public void runSimulation() {

        Random random = new Random();
        int numberOfFloors = random.nextInt(16) + 5; // 5 to 20 floors
        Building building = new Building(numberOfFloors);
        Elevator elevator = new Elevator(1); // Start from floor 1

        for (int step = 1; step <= 100; step++) {
            System.out.println("Step " + step);
            List<Passenger> passengersOnCurrentFloor = building.getPassengersOnFloor(elevator.getCurrentFloor());

            for (Passenger passenger : passengersOnCurrentFloor) {
                if (!elevator.isFull() && passenger.getDestinationFloor() >= elevator.getCurrentFloor()) {
                    elevator.boardPassenger(passenger);
                    System.out.println("Passenger boarded elevator on floor " + elevator.getCurrentFloor() +
                            " with destination " + passenger.getDestinationFloor());
                }
            }

            elevator.move();

            List<Passenger> unloadedPassengers = elevator.unloadPassengers();
            for (Passenger passenger : unloadedPassengers) {
                System.out.println("Passenger exited elevator on floor " + elevator.getCurrentFloor());
                int newDestFloor = random.nextInt(numberOfFloors - 1);
                passenger = new Passenger(elevator.getCurrentFloor(), newDestFloor);
                passengersOnCurrentFloor.add(passenger);
            }

            if (elevator.isFull() || elevator.getCurrentFloor() == numberOfFloors || elevator.getCurrentFloor() == 0) {
                elevator.move(); // Move even if full or at the top/bottom floor
            }
        }
    }
}
