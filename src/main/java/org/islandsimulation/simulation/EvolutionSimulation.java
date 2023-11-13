package org.islandsimulation.simulation;

import org.islandsimulation.animal.Animal;
import org.islandsimulation.data.FeedingData;
import org.islandsimulation.island.Island;
import org.islandsimulation.island.Location;
import org.islandsimulation.plant.Plant;
import org.islandsimulation.service.AnimalService;
import org.islandsimulation.service.PlantService;
import org.islandsimulation.ui.IslandStatePrinter;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;


public class EvolutionSimulation {
    private AnimalService animalService;
    private PlantService plantService;
    private Island island;
    private FeedingData feedingData;
    private ExecutorService executorService;
    private Map<Class<? extends Animal>, Map<Class<?>, Integer>> feeding;


    public EvolutionSimulation() {
        island = new Island();
        animalService = new AnimalService(island.getCells());
        plantService = new PlantService(island.getCells());
        this.feedingData = new FeedingData();
        feeding = feedingData.getFeedingData();

      }

    public void startSimulation() throws InterruptedException {
        for (int i = 0; i < 3; i++) {

            System.out.println("Шаг - " + (i+1));

            IslandStatePrinter.printIslandState(island, animalService, plantService);

                eat();
                awaitExecutorCompletion(executorService);
//                IslandStatePrinter.printIslandState(island, animalService, plantService);

                reproduceAnimals();
//                IslandStatePrinter.printIslandState(island, animalService, plantService);

                plantService.initializePlantCounts(island.getCells());

                move();
                awaitExecutorCompletion(executorService);
//                IslandStatePrinter.printIslandState(island, animalService, plantService);
        }
    }

    public void awaitExecutorCompletion(ExecutorService executorService) {
        try {
            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void eat() {

        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (Location location : island.getCells()) {

            List<Animal> animalsInLocation = animalService.getAnimalsInCell(location);
            Map<Class<? extends Plant>, Integer> plantsInCell = plantService.getPlantsInCell(location);

            executorService.submit(() -> {
                int i = 0;
                while (i < animalsInLocation.size()) {
                    Animal predatorClass = animalsInLocation.get(i);

                    Map<Class<?>, Integer> preyProbabilities = feeding.get(predatorClass.getClass());

                    for (Class<?> preyClass : preyProbabilities.keySet()) {
                        int probability = preyProbabilities.get(preyClass);
                        handlePredator(predatorClass, preyClass, probability, plantsInCell, animalsInLocation);
                    }
                    i++;
                }

                for (Animal animal : animalsInLocation) {
                    animal.setHasEaten(false);
                }
                animalService.setAnimalsInCell(location, animalsInLocation);

            });
        }
    }



    private void handlePredator(Animal predatorClass, Class<?> preyClass, int probability,
                                Map<Class<? extends Plant>, Integer> plantsInCell, List<Animal> animalsInLocation) {
        if (predatorClass.getCurrentSaturation() < predatorClass.getMaxSaturation() && isPreyEaten(probability)) {
            boolean foundPrey = findAndHandlePrey(predatorClass, preyClass, plantsInCell, animalsInLocation);
            if (!foundPrey) {
                handleNoPreyFound(predatorClass);
            }
        } else {
            handleNoPreyFound(predatorClass);
        }
    }

    private boolean findAndHandlePrey(Animal predatorClass, Class<?> preyClass,
                                      Map<Class<? extends Plant>, Integer> plantsInCell, List<Animal> animalsInLocation) {
        boolean foundPrey = false;

            if (Animal.class.isAssignableFrom(preyClass)){

            Iterator<Animal> iterator = animalsInLocation.iterator();
            while (iterator.hasNext()) {
                Animal preyAnimal = iterator.next();
                if (preyClass.isAssignableFrom(preyAnimal.getClass())) {

                    double weight = preyAnimal.getWeight();
                    double v = predatorClass.getMaxSaturation() - predatorClass.getCurrentSaturation();
                    int feed = 0;
                    if (weight > v) {
                        feed = (int) v;
                    } else {
                        feed = (int) weight;
                    }
                    if (predatorClass.getCurrentSaturation() + feed > predatorClass.getMaxSaturation()) {
                        feed = (int) (predatorClass.getMaxSaturation() - predatorClass.getCurrentSaturation());
                    }
                    predatorClass.setCurrentSaturation(predatorClass.getCurrentSaturation() + feed);
                    foundPrey = true;
                    iterator.remove();
                    break;
                }
            }
        } else if (preyClass.isAssignableFrom(Plant.class)) {

            if (plantsInCell.containsKey(preyClass)) {
                int plantCount = plantsInCell.get(preyClass);
                int feed = 0;
                if (plantCount > 0) {
                    double v = predatorClass.getMaxSaturation() - predatorClass.getCurrentSaturation();
                    if (plantCount > (int) v) {
                        feed = (int) v;
                    } else {
                        feed = plantCount;
                    }
                    plantsInCell.put((Class<? extends Plant>) preyClass, plantCount - feed);
                    predatorClass.setCurrentSaturation(predatorClass.getCurrentSaturation() + feed);
                    foundPrey = true;
                }
            }
        }

        if (!foundPrey) {
            handleNoPreyFound(predatorClass);
        }

        return foundPrey;
    }


    private void handleNoPreyFound(Animal predatorClass) {
        double hungerLoss = predatorClass.getMaxSaturation() * 0.1;
        predatorClass.setCurrentSaturation(predatorClass.getCurrentSaturation() - hungerLoss);
        predatorClass.setHasEaten(true);
    }

    public boolean isPreyEaten(int probability) {
        Random random = new Random();
        int randomValue = random.nextInt(100);
        return randomValue < probability;
    }

    public void move() {

        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Map<Location, List<Animal>> animalsInCell = new HashMap<>(animalService.getAnimalsInCell());
        Map<Location, List<Animal>> animalsInCellTemp = new HashMap<>();

        for (Location location : animalsInCell.keySet()) {
            List<Animal> animalsInLocation = animalsInCell.get(location);

            executorService.submit(() -> {
            List<Animal> newAnimalsInLocation = new ArrayList<>();

            for (Animal animal : animalsInLocation) {
                int totalSteps = (int) animal.getSpeed();
                int verticalSteps = ThreadLocalRandom.current().nextInt(0, totalSteps + 1);
                int horizontalSteps = ThreadLocalRandom.current().nextInt(0, totalSteps - verticalSteps + 1);

                int x = location.getX();
                int y = location.getY();

                int newX = x + ThreadLocalRandom.current().nextInt(-horizontalSteps, horizontalSteps + 1);
                int newY = y + ThreadLocalRandom.current().nextInt(-verticalSteps, verticalSteps + 1);

                int islandWidth = island.getWidth();
                int islandHeight = island.getHeight();

                newX = Math.max(0, Math.min(newX, islandWidth - 1));
                newY = Math.max(0, Math.min(newY, islandHeight - 1));

                Location newLocation = new Location(newX, newY);
                newAnimalsInLocation.add(animal);
                animalsInCellTemp.put(newLocation, newAnimalsInLocation);
            }
            });
        }

        for (Location location: animalsInCellTemp.keySet()) {
            animalService.setAnimalsInCell(location, animalsInCellTemp.get(location));
        }

    }


    public void reproduceAnimals() {
        Map<Location, List<Animal>> animalsInCellTemp = new HashMap<>(animalService.getAnimalsInCell());
        Map<Class<? extends Animal>, Integer> animalMaxPopulation = animalService.readMaxPopulations();

        for (Location location : animalsInCellTemp.keySet()) {
            List<Animal> animalsInLocation = animalsInCellTemp.get(location);

            Map<Class<? extends Animal>, Integer> animalCountMap = new HashMap<>();

            for (Animal animal : animalsInLocation) {
                Class<? extends Animal> animalClass = animal.getClass();
                animalCountMap.put(animalClass, animalCountMap.getOrDefault(animalClass, 0) + 1);
            }

            for (Class<? extends Animal> animalClass : animalCountMap.keySet()) {
                int animalCount = animalCountMap.get(animalClass);

                    int maxPopulation = animalMaxPopulation.get(animalClass);

                int childs = animalCount > 1
                        ? (animalCount + animalCount / 2) <= maxPopulation
                        ? animalCount / 2
                        : maxPopulation - animalCount
                        : 0;

                    for (int i = 0; i < childs; i++) {
                             try {
                                Animal newAnimal = animalClass.getDeclaredConstructor().newInstance();
                                 animalsInLocation.add(newAnimal);
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                    }

            }
        }

        for (Location location: animalsInCellTemp.keySet()) {
            animalService.setAnimalsInCell(location, animalsInCellTemp.get(location));
        }

    }


}






