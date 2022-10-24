package lab4.CustomerProducer;

import static lab3.CustomerProducer.Main.randomPortion;

public class Customer implements Runnable{
    private final Bakery bakery;
    private final int maxFoodPortion;

    private int receivedBuffor;
    public Customer (Bakery bakery, int maxFoodPortion) {
        this.bakery = bakery;
        this.maxFoodPortion = maxFoodPortion;
        this.receivedBuffor = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int portion = randomPortion(0, maxFoodPortion);
                this.bakery.takeBread(portion);
                this.receivedBuffor++;
                System.out.println("Bread taken " + this.receivedBuffor + " times!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Stop eating");
        }
    }
}

