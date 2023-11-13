package org.islandsimulation.animal;

import lombok.Getter;

@Getter
public abstract class Animal {

    double currentSaturation;
    double maxSaturation;
    private double speed;
    private double weight;
    private boolean hasEaten;

    public void setHasEaten(boolean b) {
        this.hasEaten = b;
    }

    public void setCurrentSaturation(double currentSaturation) {
        this.currentSaturation = currentSaturation;
    }
}