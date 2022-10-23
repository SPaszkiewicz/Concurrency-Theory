package lab3.AsyncCustomerProducer.Version1;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static int bufforSize = 20;
    static int customerNumber = 5;
    static int producentNumber = 5;

    static int foodPortion = 20;

    public static void main(String[] args) {
        manyCustomerManyProducerManyBuffor();
    }

    public static int randomPortion(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public static void manyCustomerManyProducerManyBuffor () {
        Bakery bakery = new Bakery(bufforSize, foodPortion);
        for (int i = 0; i < customerNumber; i++) {
            new Thread(new Customer(bakery, 1)).start();
        }
        for (int i = 0; i < producentNumber; i++) {
            new Thread(new Producent(bakery, 1)).start();
        }
    }
}
