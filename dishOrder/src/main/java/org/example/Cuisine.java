package org.example;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Cuisine {
    private String name;
    private List<Dish> mainDishes;
    private List<Dish> desserts;
}
