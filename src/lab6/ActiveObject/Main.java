package lab6.ActiveObject;

import lab6.ActiveObject.threads.Customer;
import lab6.ActiveObject.threads.Producent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static int bufforSize = 50;
    static int customerNumber = 5;
    static int producentNumber =  5;
    static int foodPortion = 15;
    static List<Customer> customerThreads = new ArrayList<>();

    static List<Producent> producerThreads = new ArrayList<>();

    static Random random = new Random();

    public static void main(String[] args) {
        random = new Random();
        random.setSeed(100);
//        new int[]{2000, 2000, 2000, 2000, 2000, 2000};
        for(int opsTime : new int[]{10, 20, 30, 50, 60, 70, 80, 90, 100}) {
            for(int waitTime : new int[]{10, 20, 30, 50, 60, 70, 80, 90, 100}) {
                manyCustomerManyProducerManyBuffor(opsTime, waitTime);
                customerThreads.clear();
                producerThreads.clear();
            }
        }
    }

    public static int randomPortion(int min, int max) {
        return random.nextInt(min, max + 1);
    }
    public static void manyCustomerManyProducerManyBuffor (int opsTime, int waitTime) {
        TimeOrchiester timer = new TimeOrchiester(producerThreads, customerThreads, new int[]{2000, 2000, 2000, 2000, 2000, 2000});
        Proxy proxy = new Proxy(bufforSize, timer, opsTime);
//        Producent biggerProducer = new Producent(proxy, foodPortion, -1);
//        producerThreads.add(biggerProducer);
//        new Thread(biggerProducer).start();

        for (int i = 0; i < customerNumber; i++) {
            Producent producer = new Producent(proxy, foodPortion, i, timer, waitTime);
            producerThreads.add(producer);
            new Thread(producer).start();
        }
        for (int i = 0; i < producentNumber; i++) {
            Customer customer = new Customer(proxy, foodPortion, i, timer, waitTime);
            customerThreads.add(customer);
            new Thread(customer).start();
        }
        timer.startProcessing();
    }
}