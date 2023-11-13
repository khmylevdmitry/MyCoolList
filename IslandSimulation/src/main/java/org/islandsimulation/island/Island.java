package org.islandsimulation.island;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Island {
    private List<Location> cells = new ArrayList<>();

    @Getter
    private int width;
    @Getter
    private int height;

    public Island() {
        Random random = new Random();
        this.width = 3; // в задании 100
        this.height = 2;

//        this.width = random.nextInt(10) + 1; // в задании 100
//        this.height = random.nextInt(5) + 1; // в задании - 20
        initializeIsland();
    }

    private void initializeIsland() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells.add(new Location(x, y));
            }
        }
    }

    public List<Location> getCells() {
        return cells;
    }
}
