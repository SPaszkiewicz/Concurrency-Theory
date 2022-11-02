package lab4.CustomerProducerHasWaiters;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static int bufforSize = 100;
    static int customerNumber = 5;
    static int producentNumber =  5;

    static int time = 5000;

    static boolean isStopped = false;
    static int foodPortion = 5;

    public static void main(String[] args) throws InterruptedException {
        manyCustomerManyProducerManyBuffor();
    }

    public static int randomPortion(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public static void manyCustomerManyProducerManyBuffor () throws InterruptedException {
        Bakery bakery = new Bakery(bufforSize);
        Timer timer = new Timer(time, bakery);
        new Thread(new Producent(bakery, foodPortion, -1, timer)).start();
        for (int i = 0; i < customerNumber; i++) {
            new Thread(new Customer(bakery, foodPortion, i, timer)).start();
        }
        for (int i = 0; i < producentNumber; i++) {
            new Thread(new Producent(bakery, 1, i, timer)).start();
        }
    }
}