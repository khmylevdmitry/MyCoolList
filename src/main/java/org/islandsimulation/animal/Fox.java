package org.islandsimulation.animal;

import lombok.Getter;
import org.islandsimulation.properties.AnimalProperties;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Fox extends Predator {

    private static AnimalProperties properties;
    private double currentSaturation;
    private double maxSaturation;
    private double speed;
    private double weight;
    private boolean hasEaten;

    static {
        try {
            properties = new AnimalProperties(Fox.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Fox() {
        double randomNumber = ThreadLocalRandom.current().nextDouble(properties.getMaxSaturation() / 2, properties.getMaxSaturation());
        DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
        currentSaturation = Double.parseDouble(df.format(randomNumber));
        maxSaturation = properties.getMaxSaturation();
        speed = properties.getSpeed();
        weight = properties.getWeight();
        hasEaten = false;
    }

    public void setHasEaten(boolean hasEaten) {
        this.hasEaten = hasEaten;
    }

    @Override
    public void setCurrentSaturation(double currentSaturation) {
        this.currentSaturation = currentSaturation;
    }


    @Override
    public String toString() {
        return "Fox";
    }
}