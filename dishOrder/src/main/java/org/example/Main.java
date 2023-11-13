package org.example;


public class Main {

    public static void main(String[] args) {

        MenuParsing menuParsing = new MenuParsing();
        Menu menu = menuParsing.parse();

        if(menu != null) {
            OrderInterface orderInterface = new OrderInterface(menu);
            Order order = orderInterface.createOrder();
            System.out.println(order);
        } else {
            System.out.println("Ошибка при чтении меню.");
        }

    }
}