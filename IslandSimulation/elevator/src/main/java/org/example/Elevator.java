package org.example;


import java.util.List;

class Elevator {

    protected int currentFloor = 1;
    public static final int MAX_CAPACITY = 5;
    private Building building;

    protected boolean isElevatorGoingUp;


    public Elevator(Building building) {
        this.building = building;
    }


    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int floor) {
        this.currentFloor = floor;
    }

    public boolean isElevatorGoingUp() {
        return isElevatorGoingUp;
    }

    public void setElevatorDirection(boolean goingUp) {
        this.isElevatorGoingUp = goingUp;
    }

    protected void determineElevatorDirection() {
        if (building.getElevatorPassengers().isEmpty() && noPassengersOnCurrentFloor()) {
            if (isElevatorGoingUp && !anyPassengerAboveCurrentFloor()) {
                isElevatorGoingUp = false;
            } else if (!isElevatorGoingUp && !anyPassengerBelowCurrentFloor()) {
                isElevatorGoingUp = true;
            }
        } else if (getCurrentFloor() == building.getNumOfFloors()) {
            isElevatorGoingUp = false;
        } else if (getCurrentFloor() == 1) {
            isElevatorGoingUp = true;
        } else if ((isElevatorGoingUp && noPassengersWantToGoUp()) || (!isElevatorGoingUp && noPassengersWantToGoDown())) {
            isElevatorGoingUp = !isElevatorGoingUp;
        }
    }

    boolean noPassengersOnCurrentFloor() {
        return building.floors[currentFloor - 1].isEmpty();
    }

    boolean noPassengersWantToGoUp() {
        List<Passenger> floorPassengers = building.floors[currentFloor - 1].getPassengers();
        for (Passenger p : floorPassengers) {
            if (p.getDestinationFloor() > currentFloor) {
                return false;
            }
        }
        for (Passenger p : building.getElevatorPassengers()) {
            if (p.getDestinationFloor() > currentFloor) {
                return false;
            }
        }
        return true;
    }

    boolean noPassengersWantToGoDown() {
        List<Passenger> floorPassengers = building.floors[currentFloor - 1].getPassengers();
        for (Passenger p : floorPassengers) {
            if (p.getDestinationFloor() < currentFloor) {
                return false;
            }
        }
        for (Passenger p : building.getElevatorPassengers()) {
            if (p.getDestinationFloor() < currentFloor) {
                return false;
            }
        }
        return true;
    }


    boolean anyPassengerAboveCurrentFloor() {
        for (int i = currentFloor; i < building.getNumOfFloors(); i++) {
            if (!building.floors[i].isEmpty()) {
                return true;
            }
        }
        return false;
    }

    boolean anyPassengerBelowCurrentFloor() {
        for (int i = 0; i < currentFloor - 1; i++) {
            if (!building.floors[i].isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public int findMinDestinationFloor() {
        if (building.getElevatorPassengers().isEmpty()) {
            return -1;
        }

        int minDestination = Integer.MAX_VALUE;
        for (Passenger passenger : building.getElevatorPassengers()) {
            if (passenger.getDestinationFloor() < minDestination) {
                minDestination = passenger.getDestinationFloor();
            }
        }
        return minDestination;
    }

    public int findMaxDestinationFloor() {
        if (building.getElevatorPassengers().isEmpty()) {
            return -1;
        }

        int maxDestination = Integer.MIN_VALUE;
        for (Passenger passenger : building.getElevatorPassengers()) {
            if (passenger.getDestinationFloor() > maxDestination) {
                maxDestination = passenger.getDestinationFloor();
            }
        }
        return maxDestination;
    }

    public int findClosestFloorWithPassengersGoingUp(int currentFloor) {
        for (int i = currentFloor; i < building.floors.length; i++) {
            if (!building.floors[i].isEmpty() && building.floors[i].hasPassengersGoingUp(currentFloor)) {
                return i + 1;
            }
        }
        return -1;
    }

    public int findClosestFloorWithPassengersGoingDown(int currentFloor) {
        for (int i = currentFloor - 2; i >= 0; i--) {
            if (!building.floors[i].isEmpty() && building.floors[i].hasPassengersGoingDown(currentFloor)) {
                return i + 1;
            }
        }
        return -1;
    }

    protected void moveElevator() {
        if (isElevatorGoingUp) {
            int nextFloor = findMinDestinationFloor();
            if (nextFloor != -1) {
                setCurrentFloor(nextFloor);
            } else {
                int closestUpFloor = findClosestFloorWithPassengersGoingUp(currentFloor);
                if (closestUpFloor != -1) {
                    setCurrentFloor(closestUpFloor);
                } else {
                    isElevatorGoingUp = false;
                    int closestDownFloor = findClosestFloorWithPassengersGoingDown(currentFloor);
                    if (closestDownFloor != -1) {
                        setCurrentFloor(closestDownFloor);
                    }
                }
            }
        } else {
            int nextFloor = findMaxDestinationFloor();
            if (nextFloor != -1) {
                setCurrentFloor(nextFloor);
            } else {
                int closestDownFloor = findClosestFloorWithPassengersGoingDown(currentFloor);
                if (closestDownFloor != -1) {
                    setCurrentFloor(closestDownFloor);
                } else {
                    isElevatorGoingUp = true;
                    int closestUpFloor = findClosestFloorWithPassengersGoingUp(currentFloor);
                    if (closestUpFloor != -1) {
                        setCurrentFloor(closestUpFloor);
                    }
                }
            }
        }
    }

}

