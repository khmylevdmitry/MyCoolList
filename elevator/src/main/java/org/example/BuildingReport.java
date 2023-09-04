package org.example;

import java.util.List;

public class BuildingReport {
    private Building building;
    private static int reportCounter = 0;

    public BuildingReport(Building building) {
        this.building = building;
    }


    public void displaySummary() {
        reportCounter++;
        System.out.println("* * *   Step " + reportCounter + "   * * *");

        int currentFloor = building.getCurrentFloor();
        boolean isElevatorGoingUp = building.isElevatorGoingUp();
  //      boolean isElevatorGoingUp = .isElevatorGoingUp();

        for (int i = building.getNumOfFloors() - 1; i >= 0; i--) {
            String direction = (i == currentFloor-1) ? (isElevatorGoingUp ? "^" : "v") : " ";

            List<Passenger> passengers = building.getPassengersOnFloor(i);
             StringBuilder passengersInsideElevator = new StringBuilder();

            if (i == currentFloor - 1) {
                List<Passenger> passengersInElevator = building.getElevatorPassengers();
                for (Passenger p : passengersInElevator) {
                    passengersInsideElevator.append(p.getDestinationFloor()).append(" ");
                }
            }

            StringBuilder passengersOnFloor = new StringBuilder();
            for (Passenger p : passengers) {
                if (p.getDestinationFloor() != (i + 1)) {
                    passengersOnFloor.append(p.getDestinationFloor()).append(" ");
                }
            }

            String formattedFloor = String.format("%3d", i + 1);
            String unloadedPassengers = i == (currentFloor - 1) ? String.format("%3d", building.getUnloadedPassengersCount()) : "0  ";
            String betweenBars = String.format("%-20s", direction + " " + passengersInsideElevator.toString().trim());
            String output = formattedFloor + " | " + unloadedPassengers + " | " + betweenBars + " |  " + passengersOnFloor.toString().trim();
            System.out.println(output);
        }
    }

}
