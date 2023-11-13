package org.islandsimulation.service;

import org.islandsimulation.plant.Plant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlantFinder {
    public static List<Class<? extends Plant>> findPlantClasses() {
        List<Class<? extends Plant>> plantClasses = new ArrayList<>();

        String packageName = "org.islandsimulation.plant";
        String packagePath = packageName.replace(".", "/");
        String classpath = Plant.class.getClassLoader().getResource(packagePath).getPath();
        File packageDir = new File(classpath);
        if (packageDir.isDirectory()) {
            File[] files = packageDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        try {
                            String className = packageName + "." + file.getName().replace(".class", "");
                            Class<? extends Plant> clazz = (Class<? extends Plant>) Class.forName(className);
                            plantClasses.add(clazz);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return plantClasses;
    }
}
