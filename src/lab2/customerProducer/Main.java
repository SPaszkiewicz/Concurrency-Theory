package lab2.customerProducer;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static int bufforSize = 20;

    static int maxFoodPortion = bufforSize/2;
    static int customerNumber = 2;
    static int producentNmber = 3;
    public static void main(String[] args) throws InterruptedException {
        manyCustomerManyProducerManyBuffor();
    }

    public static int randomPortion(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public static void oneCustomerOneProducerOneBuffor () {
        Bakery bakery = new Bakery(bufforSize);
        new Thread(new Customer(bakery, 1)).start();
        new Thread(new Producent(bakery, 1)).start();
    }

    public static void manyCustomerManyProducerOneBuffor () {
        Bakery bakery = new Bakery(1);
        for (int i = 0; i < customerNumber; i++) {
            new Thread(new Customer(bakery, 1)).start();
        }
        for (int i = 0; i < producentNmber; i++) {
            new Thread(new Producent(bakery, 1)).start();
        }
    }

    public static void manyCustomerManyProducerManyBuffor () {
        Bakery bakery = new Bakery(bufforSize);
        for (int i = 0; i < customerNumber; i++) {
            new Thread(new Customer(bakery, maxFoodPortion)).start();
        }
        for (int i = 0; i < producentNmber; i++) {
            new Thread(new Producent(bakery, maxFoodPortion)).start();
        }
    }
}
