package org.islandsimulation.service;

import org.islandsimulation.animal.Animal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AnimalFinder {
    public static List<Class<? extends Animal>> findAnimalClasses() {
        List<Class<? extends Animal>> animalClasses = new ArrayList<>();
        findAnimalClassesInPackage("org.islandsimulation.animal", animalClasses);
        findAnimalClassesInPackage("org.islandsimulation.herbivore", animalClasses);
        return animalClasses;
    }

    private static void findAnimalClassesInPackage(String packageName, List<Class<? extends Animal>> animalClasses) {
        String packagePath = packageName.replace(".", "/");
        String classpath = Animal.class.getClassLoader().getResource(packagePath).getPath();
        File packageDir = new File(classpath);
        if (packageDir.isDirectory()) {
            File[] files = packageDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        try {
                            String className = packageName + "." + file.getName().replace(".class", "");
                            Class<?> clazz = Class.forName(className);
                            if (Animal.class.isAssignableFrom(clazz) && !clazz.equals(Animal.class)) {
                                animalClasses.add((Class<? extends Animal>) clazz);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
