package org.islandsimulation.island;

import lombok.Data;

@Data
public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
