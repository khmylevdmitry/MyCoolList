package org.example;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class OrderInterface {
    private Scanner scanner = new Scanner(System.in);
    private Menu menu;

    public OrderInterface(Menu menu) {
        this.menu = menu;
    }

    public Order createOrder() {
        System.out.println("Хотите заказать обед? (да/нет)");
        boolean orderLunch = "да".equalsIgnoreCase(scanner.nextLine());

        System.out.println("Хотите заказать напиток? (да/нет)");
        boolean orderDrink = "да".equalsIgnoreCase(scanner.nextLine());

        Dish mainDish = null;
        Dish dessert = null;
        Drink drink = null;
        Cuisine selectedCuisine = null;
        Drink selectedDrink = null;

        if (orderLunch) {

            List<Cuisine> availableCuisines = menu.getAllCuisines();

            System.out.println("Выберите кухню (введите соответствующий номер):");

            for (int i = 0; i < availableCuisines.size(); i++) {
                System.out.println((i + 1) + ". " + availableCuisines.get(i).getName());
            }

            int cuisineChoiceIndex = scanner.nextInt() - 1;
            scanner.nextLine();

            if (cuisineChoiceIndex >= 0 && cuisineChoiceIndex < availableCuisines.size()) {
                selectedCuisine = availableCuisines.get(cuisineChoiceIndex);

            } else {
                System.out.println("Неверный выбор.");
            }

            int mainDishesSize = selectedCuisine.getMainDishes().size();
            System.out.println("Выберите основное блюдо (введите соответствующий номер): ");
            for (int i = 0; i < mainDishesSize; i++) {
                System.out.println((i + 1) + ". " + selectedCuisine.getMainDishes().get(i).getName());
            }

            int mainDishChoiceIndex = scanner.nextInt() - 1;
            scanner.nextLine();

            mainDish = selectedCuisine.getMainDishes().get(mainDishChoiceIndex);

            System.out.println("Выберите десерт (введите соответствующий номер): ");
            int desertSize = selectedCuisine.getDesserts().size();

            for (int i = 0; i < desertSize; i++) {
                System.out.println((i + 1) + ". " + selectedCuisine.getDesserts().get(i).getName());
            }

            int desertChoiceIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            dessert = selectedCuisine.getDesserts().get(desertChoiceIndex);

        }

        if (orderDrink) {

            System.out.println("Выберите напиток (введите соответствующий номер): ");

            List<String> availableDrinks = menu.getAvailableDrinks();
            for (int i = 0; i < availableDrinks.size(); i++) {
                System.out.println((i + 1) + ". " + availableDrinks.get(i));
            }

            int drinksChoiceIndex = scanner.nextInt() - 1;
            scanner.nextLine();

            if (drinksChoiceIndex >= 0 && drinksChoiceIndex < availableDrinks.size()) {
                String drinkName = availableDrinks.get(drinksChoiceIndex);
                Optional<Drink> optionalDrink = menu.getDrinkByName(drinkName);
                if (optionalDrink.isPresent()) {
                    selectedDrink = optionalDrink.get();
                } else {
                    System.out.println("Такого напитка нет в меню.");
                    return null;
                }
            } else {
                System.out.println("Неверный выбор.");
            }

            System.out.println("Желаете добавить кубики льда? (да/нет)");
            boolean hasIce = "да".equalsIgnoreCase(scanner.nextLine());

            System.out.println("Желаете добавить лимон? (да/нет)");
            boolean hasLemon = "да".equalsIgnoreCase(scanner.nextLine());

            drink = new Drink(selectedDrink.getName(), selectedDrink.getPrice(), hasIce, hasLemon);
        }

        return Order.builder()
                .mainDish(mainDish)
                .dessert(dessert)
                .drink(drink)
                .build();
    }
}


