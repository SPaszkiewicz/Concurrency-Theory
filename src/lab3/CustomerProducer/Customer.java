package lab3.CustomerProducer;


import lab3.CustomerProducer.Bakery;
import lab3.CustomerProducer.Main;

public class Customer implements Runnable{
    private final Bakery bakery;
    private int maxFoodPortion;
    public Customer (Bakery bakery, int maxFoodPortion) {
        this.bakery = bakery;
        this.maxFoodPortion = maxFoodPortion;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.bakery.takeBread(1);
                System.out.println("Bread taken");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Stop eating");
        }
    }
}

