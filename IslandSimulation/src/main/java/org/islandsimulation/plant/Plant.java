package org.islandsimulation.plant;

import lombok.Getter;
import org.islandsimulation.properties.PlantProperties;

import java.io.IOException;


public class Plant {

    @Getter
    private static final PlantProperties properties;

    static {
        try {
            String className = Plant.class.getSimpleName().toLowerCase();
            properties = new PlantProperties(className);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Plant";
    }
}