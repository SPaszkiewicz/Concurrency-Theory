package lab4.CustomerProducerHasWaiters;

import static lab3.CustomerProducer.Main.randomPortion;

public class Producent implements Runnable {
    private final Bakery bakery;
    private final int maxFoodPortion;

    private final Timer timer;
    private int receivedBuffor;
    private final int index;

    public Producent(Bakery bakery, int maxFoodPortion, int index, Timer timer) {
        this.bakery = bakery;
        this.maxFoodPortion = maxFoodPortion;
        this.receivedBuffor = 0;
        this.index = index;
        this.timer = timer;
    }

    @Override
    public void run() {
        while (!timer.isStopped()) {
            System.out.println("Stop baking");
            try {
                int portion = randomPortion(0, maxFoodPortion);
                this.bakery.addBread(portion);
                this.bakery.printBakery();
                this.receivedBuffor++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Bread added " + this.receivedBuffor + " times at " + this.index);
    }
}
