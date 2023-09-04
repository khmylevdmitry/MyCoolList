package org.example;

import java.util.*;


class Building {

    protected Floor[] floors;
    Elevator elevator;
    int numFloors;
    private int unloadedPassengersCount = 0;
    private int currentFloor = 1;
    private List<Passenger> elevatorPassengers = new ArrayList<>();


    public Building(int numFloors) {
        this.numFloors = numFloors;
        this.floors = new Floor[numFloors];
        for (int i = 0; i < numFloors; i++) {
            floors[i] = new Floor();
        }
        this.elevator = new Elevator(this);
    }

     public void addPassengersToFloor(int floorNum, int numPassengers, List<Integer> destinationFloors) {
        floors[floorNum].addPassengers(numPassengers, destinationFloors);
    }

    public int getNumOfFloors() {
        return floors.length;
    }

    public List<Passenger> getPassengersOnFloor(int floorNum) {
        return floors[floorNum].getPassengers();
    }

    public int getCurrentFloor() {
        return elevator.getCurrentFloor();
    }

    public boolean isElevatorGoingUp() {
        return elevator.isElevatorGoingUp();
    }

    public void unloadPassengers(int floor) {

        Iterator<Passenger> iterator = elevatorPassengers.iterator();
        int passengersExitingNow = 0;
        while (iterator.hasNext()) {
            Passenger p = iterator.next();
            if (p.getDestinationFloor() == floor) {
                passengersExitingNow++;
                iterator.remove();
            }
        }
        unloadedPassengersCount = passengersExitingNow;
    }

    public int getUnloadedPassengersCount() {
        return unloadedPassengersCount;
    }

    void startElevator() {
        BuildingReport report = new BuildingReport(this);
        elevator.setElevatorDirection(true);
        report.displaySummary();

        while (!areAllPassengersServed()) {
            unloadPassengers(getCurrentFloor());

            if (elevatorPassengers.size() < Elevator.MAX_CAPACITY) {
                loadPassengersFromCurrentFloor();
            }

            report.displaySummary();

            elevator.determineElevatorDirection();
            loadPassengersIfNeeded();
            elevator.moveElevator();

         }
    }

    private void loadPassengersIfNeeded() {
        if (elevatorPassengers.isEmpty()) {
            if (isElevatorGoingUp() && floors[getCurrentFloor() - 1].hasPassengersGoingUp(getCurrentFloor())) {
                loadPassengersFromCurrentFloor();
            } else if (!isElevatorGoingUp() && floors[getCurrentFloor() - 1].hasPassengersGoingDown(getCurrentFloor())) {
                loadPassengersFromCurrentFloor();
            }
        }
    }

    public boolean areAllPassengersServed() {
        if (!elevatorPassengers.isEmpty()) {
            return false;
        }

        for (Floor floor : floors) {
            List<Passenger> floorPassengers = floor.getPassengers();
            if (!floorPassengers.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void loadPassengersFromCurrentFloor() {

        List<Passenger> floorPassengers = floors[getCurrentFloor() - 1].getPassengers();

        Iterator<Passenger> iterator = floorPassengers.iterator();
        while (iterator.hasNext() && elevatorPassengers.size() < 5) {
            Passenger passenger = iterator.next();

            if (getCurrentFloor() == numFloors || getCurrentFloor() == 1) {
                elevatorPassengers.add(passenger);
                iterator.remove();
            } else {

                if ((isElevatorGoingUp() && passenger.getDestinationFloor() > getCurrentFloor()) ||
                        (!isElevatorGoingUp() && passenger.getDestinationFloor() < getCurrentFloor())) {
                    elevatorPassengers.add(passenger);
                    iterator.remove();
                }
            }
        }
    }


    public List<Passenger> getElevatorPassengers() {
        return elevatorPassengers;
    }


}

