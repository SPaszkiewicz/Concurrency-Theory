package lab6.ActiveObject;

import lab6.ActiveObject.threads.Customer;
import lab6.ActiveObject.threads.Producent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static int bufforSize = 50;
    static int customerNumber = 5;
    static int producentNumber =  5;
    static int foodPortion = 15;
    static List<Customer> customerThreads = new ArrayList<>();

    static List<Producent> producerThreads = new ArrayList<>();

    public static void main(String[] args) {
        manyCustomerManyProducerManyBuffor();
    }

    public static int randomPortion(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public static void manyCustomerManyProducerManyBuffor () {
        Proxy proxy = new Proxy(bufforSize);
//        Producent biggerProducer = new Producent(proxy, foodPortion, -1);
//        producerThreads.add(biggerProducer);
//        new Thread(biggerProducer).start();

        for (int i = 0; i < customerNumber; i++) {
            Producent producer = new Producent(proxy, foodPortion, i);
            producerThreads.add(producer);
            new Thread(producer).start();
        }
        for (int i = 0; i < producentNumber; i++) {
            Customer customer = new Customer(proxy, foodPortion, i);
            customerThreads.add(customer);
            new Thread(customer).start();
        }
//        TimeOrchiester timer = new TimeOrchiester(producerThreads, customerThreads, new int[]{1000, 2000, 5000, 7000, 10000, 20000});
//        while (true) {
//            timer.startProcessing();
//        }
    }
}