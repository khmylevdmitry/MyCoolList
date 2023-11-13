package org.islandsimulation.data;

import org.islandsimulation.animal.Animal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FeedingData {

    private Map<Class<? extends Animal>, Map<Class<?>, Integer>> feeding;

    public FeedingData() {
        feeding = new HashMap<>();
        initializeEatAnimalProbabilities();
    }

    private void initializeEatAnimalProbabilities() {
        File configDirectory = new File("src/main/resources/config/eating");
        File[] files = configDirectory.listFiles((dir, name) -> name.endsWith(".properties"));

        for (File file : files) {
            String whoIsEating = file.getName().replace(".properties", "");
            whoIsEating = whoIsEating.substring(0, 1).toUpperCase() + whoIsEating.substring(1);

            Class<? extends Animal> whoIsEatingClass = getAnimalClassFromName(whoIsEating);

            if (whoIsEatingClass != null) {
                Properties properties = new Properties();
                try (InputStream input = new FileInputStream(file)) {
                    properties.load(input);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                Map<Class<?>, Integer> preyProbabilities = new HashMap<>();
                for (String animalName : properties.stringPropertyNames()) {
                    String animalName1 = animalName.substring(0, 1).toUpperCase() + animalName.substring(1);
                    Class<?> preyClass = getClassFromName(animalName1);
                    if (preyClass != null) {
                        String probabilityStr = properties.getProperty(animalName);
                        if (probabilityStr != null && !probabilityStr.isEmpty()) {
                            int probability = Integer.parseInt(probabilityStr);
                            preyProbabilities.put(preyClass, probability);
                        }
                    }
                }

                feeding.put(whoIsEatingClass, preyProbabilities);
            }
        }
    }

    private Class<?> getClassFromName(String animalName1) {
        try {
            String animalClassPath = "org.islandsimulation.animal." + animalName1;
            if (!isClassExists(animalClassPath)) {
                animalClassPath = "org.islandsimulation.herbivore." + animalName1;
            }
            if (!isClassExists(animalClassPath)) {
                animalClassPath = "org.islandsimulation.plant." + animalName1;
            }
            Class<?> clazz = Class.forName(animalClassPath);
            return (Class<?>) clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Class<? extends Animal> getAnimalClassFromName(String animalName) {
        try {
            String animalClassPath = "org.islandsimulation.animal." + animalName;
            if (!isClassExists(animalClassPath)) {
                animalClassPath = "org.islandsimulation.herbivore." + animalName;
            }
            Class<?> clazz = Class.forName(animalClassPath);

            if (Animal.class.isAssignableFrom(clazz)) {
                return (Class<? extends Animal>) clazz;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean isClassExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }

    }

    public Map<Class<? extends Animal>, Map<Class<?>, Integer>> getFeedingData() {
        return feeding;
    }
}

