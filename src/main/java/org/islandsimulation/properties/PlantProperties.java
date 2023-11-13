package org.islandsimulation.properties;

import lombok.Getter;
import org.islandsimulation.util.ConfigurationManager;

import java.io.IOException;
import java.util.Properties;

public class PlantProperties {
    @Getter
    private final double weight;

    public PlantProperties(String className) throws IOException {
        Properties properties = ConfigurationManager.loadProperties(className + ".properties");
        this.weight = Double.parseDouble(properties.getProperty("weight"));
    }
}