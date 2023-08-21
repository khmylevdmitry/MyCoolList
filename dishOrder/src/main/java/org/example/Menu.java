package org.example;

import lombok.Data;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class Menu {
    private List<Cuisine> cuisines;
    private List<Drink> drinks;

    public List<String> getAvailableDrinks() {
        return drinks.stream()
                .map(Drink::getName)
                .collect(Collectors.toList());
    }

    public Optional<Drink> getDrinkByName(String drinkName) {
        return drinks.stream()
                .filter(drink -> drink.getName().equalsIgnoreCase(drinkName))
                .findFirst();
    }

    public List<Cuisine> getAllCuisines() {
        return cuisines;
    }
}
