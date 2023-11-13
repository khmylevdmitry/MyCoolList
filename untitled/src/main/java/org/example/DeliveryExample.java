package org.example;

public class DeliveryExample {
    public static void main(String[] args) {
        Truck truck = new Truck();
        Shop shop = new Shop(truck);
        Buyers buyers = new Buyers(shop);

        Loader loader = new Loader(truck);

        Thread loaderThread = new Thread(loader);
        Thread truckThread = new Thread(truck);
        Thread shopThread = new Thread(shop);
        Thread buyersThread = new Thread(buyers);

        loaderThread.start();
        truckThread.start();
        shopThread.start();
        buyersThread.start();
    }
}

class Truck implements Runnable {
    private int boxes = 0;

    public synchronized void loadBox() {
        boxes++;
        System.out.println("Loader: Loaded 1 box, total boxes: " + boxes);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(15000); // Время доставки
                synchronized (this) {
                    int boxesToDeliver = boxes;
                    boxes = 0;
                    System.out.println("Truck: Delivering " + boxesToDeliver + " boxes to the shop.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized int unloadBox() {
        int unloadedBoxes = boxes;
        boxes = 0;
        return unloadedBoxes;
    }
}

class Shop implements Runnable {
    private Truck truck;
    private int stock = 0;

    public Shop(Truck truck) {
        this.truck = truck;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); // Время разгрузки в магазин
                synchronized (truck) {
                    int unloadedBoxes = truck.unloadBox();
                    stock += unloadedBoxes;
                    System.out.println("Shop: Unloaded " + unloadedBoxes + " boxes, stock: " + stock);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void sellBox() {
        if (stock > 0) {
            stock--;
            System.out.println("Shop: Sold 1 box, remaining stock: " + stock);
        }
    }
}

class Buyers implements Runnable {
    private Shop shop;

    public Buyers(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); // Время покупки
                shop.sellBox(); // Покупатели покупают коробки из магазина
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Loader implements Runnable {
    private Truck truck;

    public Loader(Truck truck) {
        this.truck = truck;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); // Время загрузки
                truck.loadBox();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
