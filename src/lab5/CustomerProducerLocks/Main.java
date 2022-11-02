package lab5.CustomerProducerLocks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static int bufforSize = 500;
    static int customerNumber = 1;
    static int producentNumber =  3;

    static int foodPortion = 100;

    static List<Thread> customerThreads = new ArrayList<Thread>();

    static List<Thread> producerThreads = new ArrayList<Thread>();

    public static void main(String[] args) {
        manyCustomerManyProducerManyBuffor();
    }

    public static int randomPortion(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public static void manyCustomerManyProducerManyBuffor () {
        Bakery bakery = new Bakery(bufforSize);

        Thread biggerThread = new Thread(new Producent(bakery, foodPortion, -1));
        producerThreads.add(biggerThread);
        biggerThread.start();

        for (int i = 0; i < customerNumber; i++) {
            Thread thread = new Thread(new Customer(bakery, foodPortion, i));
            customerThreads.add(thread);
            thread.start();
        }
        for (int i = 0; i < producentNumber; i++) {
            Thread thread = new Thread(new Producent(bakery, foodPortion, i));
            producerThreads.add(thread);
            thread.start();
        }
    }
}