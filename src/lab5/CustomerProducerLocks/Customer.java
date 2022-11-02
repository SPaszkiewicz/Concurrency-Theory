package lab5.CustomerProducerLocks;

import static lab3.CustomerProducer.Main.randomPortion;

public class Customer implements Runnable{
    private final Bakery bakery;
    private final int maxFoodPortion;
    private final int index;
    private int receivedBuffor;
    public Customer (Bakery bakery, int maxFoodPortion, int index) {
        this.bakery = bakery;
        this.maxFoodPortion = maxFoodPortion;
        this.receivedBuffor = 0;
        this.index = index;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int portion = randomPortion(0, maxFoodPortion);
                this.bakery.takeBread(portion);
                this.receivedBuffor++;
                System.out.println("Bread taken " + this.receivedBuffor + " times at " + this.index);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

