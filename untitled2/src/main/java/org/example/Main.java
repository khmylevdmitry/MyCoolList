package org.example;

public class Main {
    public static void main(String[] args) {

        Object lock = new Object();
        Box box = new Box(lock);

        Thread loaderThread = new Thread(new Loader(box, lock));
        Thread carThread = new Thread(new Car(box, lock));
        Thread storeThread = new Thread(new Store(box, lock));
        Thread buyersThread = new Thread(new Buyers(box, lock));

        loaderThread.start();
        carThread.start();
        storeThread.start();
        buyersThread.start();
    }
}

class Box {
    private int availableBoxes = 0;
    private Object lock;

    public Box(Object lock) {
        this.lock = lock;
    }

    public void loadBoxes(int numBoxes) throws InterruptedException {
        synchronized (lock) {
            availableBoxes += numBoxes;
            for (int i = 1; i < numBoxes + 1; i++) {
                System.out.println("Грузчик загрузил в авто " + i + " коробок. ");
                Thread.sleep(100);
            }
        }
    }

    public int takeBoxes(int numBoxes) {
//        synchronized (lock) {
            int takenBoxes = Math.min(numBoxes, availableBoxes);
//            availableBoxes -= takenBoxes;
//            System.out.println("Магазин отдал " + takenBoxes + " коробок покупателям. Осталось " + availableBoxes + " коробок.");
            return takenBoxes;
 //      }
    }
}

class Loader implements Runnable {
    private Box box;
    private Object lock;

    public Loader(Box box, Object lock) {
        this.box = box;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                try {
                    box.loadBoxes(40);
                    Car.isLoaded = true; // Устанавливаем флаг, что загрузка завершена
                    lock.notify(); // Уведомление других потоков
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000); // Задержка между загрузками
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Car implements Runnable {
    private Box box;
    private Object lock;
    public static boolean isLoaded = false;
  //  private static boolean isLoaded;

    public Car(Box box, Object lock) {
        this.box = box;
        this.lock = lock;
    }

    private static boolean isDelivered = false;

    public static void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public static boolean isDelivered() {
        return isDelivered;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                while (!isLoaded) {
                    try {
                        lock.wait(); // Ожидание, пока isLoaded не станет true
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int boxesToDeliver = box.takeBoxes(40);
                System.out.println("Автомобиль доставил " + boxesToDeliver + " коробок в магазин.");
                isLoaded = false; // Сбрасываем флаг после доставки
                isDelivered = true; // Устанавливаем флаг, что доставка завершена
                lock.notifyAll(); // Уведомление других потоков
            }
            try {
                Thread.sleep(15000); // Задержка между доставками
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Store implements Runnable {
    private Box box;
    private Object lock;

    public Store(Box box, Object lock) {
        this.box = box;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                while (!Car.isDelivered()) {
                    try {
                        lock.wait(); // Ожидание, пока isDelivered не станет true
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int boxesToDeliver = box.takeBoxes(40);
                System.out.println("Магазин выставил на продажу " + boxesToDeliver + " коробок.");
                Car.setDelivered(false); // Сбрасываем флаг
                lock.notifyAll();
            }
        }
    }
}

class Buyers implements Runnable {
    private Box box;
    private Object lock;

    public Buyers(Box box, Object lock) {
        this.box = box;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
//            synchronized (lock) {
//                int takenBoxes = box.takeBoxes(1);
//                System.out.println("Покупатель купил " + takenBoxes + " коробку.");
//            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}