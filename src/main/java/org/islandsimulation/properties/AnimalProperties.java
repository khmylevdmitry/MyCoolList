package org.islandsimulation.properties;

import org.islandsimulation.animal.Animal;
import org.islandsimulation.util.ConfigurationManager;

import java.io.IOException;
import java.util.Properties;

public class AnimalProperties {

    private final Properties properties;

    public AnimalProperties(String className) throws IOException {
        this.properties = ConfigurationManager.loadProperties(className + ".properties");
    }

    public double getWeight() {
        return Double.parseDouble(properties.getProperty("weight"));
    }

    public double getSpeed() {
        return Double.parseDouble(properties.getProperty("speed"));
    }

    public double getMaxSaturation() {
        return Double.parseDouble(properties.getProperty("max_saturation"));
    }

    public AnimalProperties(Class<? extends Animal> animalClass) throws IOException {
        this(animalClass.getSimpleName().toLowerCase());
    }
}
