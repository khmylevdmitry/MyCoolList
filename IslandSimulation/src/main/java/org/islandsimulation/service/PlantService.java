package org.islandsimulation.service;

import org.islandsimulation.island.Location;
import org.islandsimulation.plant.Plant;
import org.islandsimulation.util.ConfigurationManager;

import java.io.IOException;
import java.util.*;

public class PlantService {
    private Map<Location, Map<Class<? extends Plant>, Integer>> plantsInCell;

    public PlantService(List<Location> locations) {
        this.plantsInCell = new HashMap<>();
        initializePlantCounts(locations);
    }

    public void initializePlantCounts(List<Location> locations) {
        Map<Class<? extends Plant>, Integer> maxPopulations = readMaxPopulations();
        List<Class<? extends Plant>> plantClasses = PlantFinder.findPlantClasses();

        for (Location location : locations) {
            plantsInCell.put(location, new HashMap<>());
            for (Class<? extends Plant> plantClass : plantClasses) {
                int maxPopulation = maxPopulations.get(plantClass);
                int count = generateRandomPlantCount(maxPopulation);
                plantsInCell.get(location).put(plantClass, count);
            }
        }
    }

    private Map<Class<? extends Plant>, Integer> readMaxPopulations() {
        Map<Class<? extends Plant>, Integer> maxPopulations = new HashMap<>();
        try {
            Properties maxPopulationProperties = ConfigurationManager.loadProperties("population/maxpopulation.properties");

            List<Class<? extends Plant>> plantClasses = PlantFinder.findPlantClasses();

            for (Class<? extends Plant> plantClass : plantClasses) {
                String className = plantClass.getSimpleName().toLowerCase();
                Object maxPopulationValue = maxPopulationProperties.get(className);
                String maxPopulationString = maxPopulationValue != null ? maxPopulationValue.toString() : "0";
                int maxPopulation = Integer.parseInt(maxPopulationString);
                maxPopulations.put(plantClass, maxPopulation);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return maxPopulations;
    }

    private int generateRandomPlantCount(int maxPopulation) {
        Random random = new Random();
        return random.nextInt(maxPopulation + 1);
    }

    public Map<Class<? extends Plant>, Integer> getPlantsInCell(Location location) {
        return plantsInCell.get(location);
    }
}
