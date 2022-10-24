package lab4.CustomerProducer4Con;

import static lab3.CustomerProducer.Main.randomPortion;

public class Producent implements Runnable {
    private final Bakery bakery;
    private final int maxFoodPortion;

    private int receivedBuffor;
    private final int index;

    public Producent(Bakery bakery, int maxFoodPortion, int index) {
        this.bakery = bakery;
        this.maxFoodPortion = maxFoodPortion;
        this.receivedBuffor = 0;
        this.index = index;
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
                System.out.println("Bread added " + this.receivedBuffor + " times at " + this.index);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
