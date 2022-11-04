package lab5.CustomerProducer4Con;

import static lab3.CustomerProducer.Main.randomPortion;

public class Producent implements Runnable {
    private final Bakery bakery;
    private final int maxFoodPortion;
    private int receivedBuffor;
    private final int index;

    public int getReceivedBuffor() {
        return receivedBuffor;
    }

    public Producent(Bakery bakery, int maxFoodPortion, int index) {
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
                this.bakery.addBread(portion);
                this.receivedBuffor++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
