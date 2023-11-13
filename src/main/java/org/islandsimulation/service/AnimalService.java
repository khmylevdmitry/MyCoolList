package org.islandsimulation.service;

import org.islandsimulation.animal.Animal;
import org.islandsimulation.island.Location;
import org.islandsimulation.util.ConfigurationManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

    public class AnimalService {

        private Map<Location, List<Animal>> animalsInCell;
        private Set<Class<? extends Animal>> animalClasses;

        public AnimalService(List<Location> locations) {
            this.animalsInCell = new HashMap<>();
            this.animalClasses = new HashSet<>();
            initializeAnimalCounts(locations);
        }

    private void initializeAnimalCounts(List<Location> locations) {
        animalClasses.addAll(AnimalFinder.findAnimalClasses()); // Добавляем классы животных в animalClasses
        Map<Class<? extends Animal>, Integer> maxPopulations = readMaxPopulations();

        for (Location location : locations) {
            animalsInCell.put(location, new ArrayList<>());
            for (Class<? extends Animal> animalClass : maxPopulations.keySet()) {
                int maxPopulation = maxPopulations.get(animalClass);
                int count = generateRandomAnimalCount(maxPopulation);
                for (int i = 0; i < count; i++) {
                    try {
                        Animal animal = animalClass.getDeclaredConstructor().newInstance(); // Создаем экземпляр класса животного
                        animalsInCell.get(location).add(animal); // Добавляем экземпляр в список животных
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                             InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


         public Map<Class<? extends Animal>, Integer> readMaxPopulations() {
        Map<Class<? extends Animal>, Integer> maxPopulations = new HashMap<>();
        try {
            Properties maxPopulationProperties = ConfigurationManager.loadProperties("population/maxpopulation.properties");

            for (Class<? extends Animal> animalClass : animalClasses) {
                String className = animalClass.getSimpleName().toLowerCase();
                Object maxPopulationValue = maxPopulationProperties.get(className);
                String maxPopulationString = maxPopulationValue != null ? maxPopulationValue.toString() : "0";
                int maxPopulation = Integer.parseInt(maxPopulationString);
                maxPopulations.put(animalClass, maxPopulation);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return maxPopulations;
    }

    private int generateRandomAnimalCount(int maxPopulation) {
        Random random = new Random();
        return random.nextInt(maxPopulation + 1);
    }

    public List<Animal> getAnimalsInCell(Location location) {
        List<Animal> animalsInLocation = animalsInCell.get(location);
        if (animalsInLocation != null) {
            return animalsInLocation;
        } else {
            return Collections.emptyList();
        }
    }


    public Set<Class<? extends Animal>> getAnimals() {
        return animalClasses;
    }

    public Map<Location, List<Animal>> getAnimalsInCell() {
        return animalsInCell;
    }

    public void setAnimalsInCell(Location location, List<Animal> animals) {
        animalsInCell.put(location, animals);
    }


}
