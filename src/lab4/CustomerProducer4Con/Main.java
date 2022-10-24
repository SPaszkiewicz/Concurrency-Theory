package lab4.CustomerProducer4Con;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static int bufforSize = 10000;
    static int customerNumber = 1;
    static int producentNumber =  3;

    static int foodPortion = 5000;

    public static void main(String[] args) {
        manyCustomerManyProducerManyBuffor();
    }

    public static int randomPortion(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public static void manyCustomerManyProducerManyBuffor () {
        Bakery bakery = new Bakery(bufforSize, foodPortion);
        new Thread(new Producent(bakery, foodPortion, -1)).start();
        for (int i = 0; i < customerNumber; i++) {
            new Thread(new Customer(bakery, foodPortion, i)).start();
        }
        for (int i = 0; i < producentNumber; i++) {
            new Thread(new Producent(bakery, 1, i)).start();
        }
    }
}
