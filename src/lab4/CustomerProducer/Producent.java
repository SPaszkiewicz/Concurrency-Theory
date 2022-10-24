package lab4.CustomerProducer;

import static lab3.CustomerProducer.Main.randomPortion;

public class Producent implements Runnable {
    private final Bakery bakery;
    private final int maxFoodPortion;

    private int receivedBuffor;

    public Producent(Bakery bakery, int maxFoodPortion) {
        this.bakery = bakery;
        this.maxFoodPortion = maxFoodPortion;
        this.receivedBuffor = 0;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Stop baking");
            try {
                int portion = randomPortion(0, maxFoodPortion);
                this.bakery.addBread(portion);
                this.bakery.printBakery();
                this.receivedBuffor++;
                System.out.println("Bread added " + this.receivedBuffor + " times!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
