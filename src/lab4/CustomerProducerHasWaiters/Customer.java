package lab4.CustomerProducerHasWaiters;

import static lab3.CustomerProducer.Main.randomPortion;

public class Customer implements Runnable{
    private final Bakery bakery;
    private final int maxFoodPortion;
    private final int index;

    private final Timer timer;
    private int receivedBuffor;
    public Customer (Bakery bakery, int maxFoodPortion, int index, Timer timer) {
        this.bakery = bakery;
        this.maxFoodPortion = maxFoodPortion;
        this.receivedBuffor = 0;
        this.index = index;
        this.timer = timer;
    }

    @Override
    public void run() {
        while (!timer.isStopped()) {
            try {
                int portion = randomPortion(0, maxFoodPortion);
                this.bakery.takeBread(portion);
                this.receivedBuffor++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Stop eating");

        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Bread taken " + this.receivedBuffor + " times at " + this.index);
    }
}

