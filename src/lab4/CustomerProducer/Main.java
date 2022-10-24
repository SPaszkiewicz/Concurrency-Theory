package lab4.CustomerProducer;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static int bufforSize = 10000;
    static int customerNumber = 300000;
    static int producentNumber =  300000;

    static int foodPortion = 5000;

    public static void main(String[] args) {
        manyCustomerManyProducerManyBuffor();
    }

    public static int randomPortion(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public static void manyCustomerManyProducerManyBuffor () {
        Bakery bakery = new Bakery(bufforSize, foodPortion);
        new Thread(new Producent(bakery, foodPortion)).start();
        for (int i = 0; i < customerNumber; i++) {
            new Thread(new Customer(bakery, foodPortion)).start();
        }
        for (int i = 0; i < producentNumber; i++) {
            new Thread(new Producent(bakery, 1)).start();
        }
    }
}
