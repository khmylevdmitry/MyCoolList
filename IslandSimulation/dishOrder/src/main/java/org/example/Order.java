package org.example;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Order {
    private Dish mainDish;
    private Dish dessert;
    private Drink drink;

    @Override
    public String toString() {
        String result = "\n" + "Ваш заказ :" + "\n";
        double cost = 0;

        if (mainDish != null) {
            result += "Основное блюдо - " + mainDish.getName() + " (стоимость - " + mainDish.getPrice() +")"+ "\n";
            cost += mainDish.getPrice();
        }

        if (dessert != null) {
            result += "десерт - " + dessert.getName() + " (стоимость - " + dessert.getPrice() +")"+ "\n";
            cost += dessert.getPrice();
        }

        if (drink != null) {
            result += "напиток - " + drink.getName() + " (стоимость - " + drink.getPrice() +")" ;
            cost += drink.getPrice();
            result += drink.isAllowsIce() ? " с льдом" : " без льда";
            result += drink.isHasLemon() ? " с лимоном" : " без лимона";
        }

        return result +  "\n" + "На общую сумму - " + cost;
    }
}


