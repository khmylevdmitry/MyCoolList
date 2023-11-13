package org.islandsimulation.ui;

import org.islandsimulation.animal.*;
import org.islandsimulation.herbivore.*;
import org.islandsimulation.island.Location;
import org.islandsimulation.plant.Plant;
import org.islandsimulation.service.AnimalService;
import org.islandsimulation.island.Island;
import org.islandsimulation.service.PlantService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class IslandStatePrinter {
    public static void printIslandState(Island island, AnimalService animalService, PlantService plantService) {
        for (Location location : island.getCells()) {
            List<Animal> animalsInLocation = animalService.getAnimalsInCell(location);
            Map<Class<? extends Plant>, Integer> plantsInLocation = plantService.getPlantsInCell(location);

            System.out.println("Location: " + location.getX() + ", " + location.getY());

            Map<Class<? extends Animal>, Integer> animalClassCounts = new HashMap<>();

            Iterator<Animal> animalIterator = animalsInLocation.iterator();
            while (animalIterator.hasNext()) {
                Animal animal = animalIterator.next();
                animalClassCounts.put(animal.getClass(), animalClassCounts.getOrDefault(animal.getClass(), 0) + 1);
            }

            for (Map.Entry<Class<? extends Animal>, Integer> entry : animalClassCounts.entrySet()) {
                Class<? extends Animal> animalClass = entry.getKey();
                int count = entry.getValue();
                String animalSymbol = getAnimalSymbol(animalClass);
                if (!animalSymbol.isEmpty()) {
                    System.out.print(animalSymbol + " - " + count + ",  ");
                }
            }

            for (Map.Entry<Class<? extends Plant>, Integer> entry : plantsInLocation.entrySet()) {
                Class<? extends Plant> plantClass = entry.getKey();
                int count = entry.getValue();
                String plantSymbol = getPlantSymbol(plantClass);
                if (!plantSymbol.isEmpty()) {
                    System.out.print(plantSymbol + " - " + count + ",  ");
                }
            }

            System.out.println();
        }

}

    private static String getAnimalSymbol(Class<? extends Animal> animalClass) {
        if (animalClass.equals(Buffalo.class)) return "🐃";
        else if (animalClass.equals(Bear.class)) return "🐻";
        else if (animalClass.equals(Horse.class)) return "🐎";
        else if (animalClass.equals(Deer.class)) return "🦌";
        else if (animalClass.equals(Boar.class)) return "🐗";
        else if (animalClass.equals(Sheep.class)) return "🐑";
        else if (animalClass.equals(Goat.class)) return "🐐";
        else if (animalClass.equals(Wolf.class)) return "🐺";
        else if (animalClass.equals(Snake.class)) return "🐍";
        else if (animalClass.equals(Fox.class)) return "🦊";
        else if (animalClass.equals(Eagle.class)) return "🦅";
        else if (animalClass.equals(Rabbit.class)) return "🐇";
        else if (animalClass.equals(Duck.class)) return "🦆";
        else if (animalClass.equals(Mouse.class)) return "🐁";
        else if (animalClass.equals(Caterpillar.class)) return "🐛";

        return "";
    }

    private static String getPlantSymbol(Class<? extends Plant> plantClass) {
        if (plantClass.equals(Plant.class)) return "🌿";
        return "";
    }
}
