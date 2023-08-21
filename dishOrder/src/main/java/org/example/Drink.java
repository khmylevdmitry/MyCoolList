package org.example;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Drink {
    private String name;
    private double price;
    private boolean allowsIce;
    private boolean hasLemon;

    public Drink(String name, double price, boolean allowsIce, boolean hasLemon) {
        this.name = name;
        this.price = price;
        this.allowsIce = allowsIce;
        this.hasLemon = hasLemon;
    }
}