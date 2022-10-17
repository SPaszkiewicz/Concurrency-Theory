package lab3.CustomerProducer;

public class Producent implements Runnable {
    private final Bakery bakery;
    private int maxFoodPortion;

    public Producent(Bakery bakery, int maxFoodPortion) {
        this.bakery = bakery;
        this.maxFoodPortion = maxFoodPortion;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Stop baking");
            try {
                this.bakery.addBread(1);
                this.bakery.printBakery();
                System.out.println("Bread added");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
