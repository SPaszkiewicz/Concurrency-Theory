package lab3.CustomerProducer;

import lab3.CustomerProducer.Bakery;
import lab3.CustomerProducer.Customer;
import lab3.CustomerProducer.Producent;

public class Main {
    static int bufforSize = 1;
    static int customerNumber = 2;
    static int producentNmber = 3;

    public static void main(String[] args) {
        manyCustomerManyProducerManyBuffor();
    }

    public static void manyCustomerManyProducerManyBuffor () {
        Bakery bakery = new Bakery(bufforSize);
        for (int i = 0; i < customerNumber; i++) {
            new Thread(new Customer(bakery, 1)).start();
        }
        for (int i = 0; i < producentNmber; i++) {
            new Thread(new Producent(bakery, 1)).start();
        }
    }
}
