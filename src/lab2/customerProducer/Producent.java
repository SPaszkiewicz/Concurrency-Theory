package lab2.customerProducer;

public class Producent implements Runnable {
    private Bakery bakery;
    private int maxFoodPortion;

    public Producent(Bakery bakery, int maxFoodPortion) {
        this.bakery = bakery;
        this.maxFoodPortion = maxFoodPortion;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println("Stop baking");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                int breadTaken = Main.randomPortion(1, maxFoodPortion);
                this.bakery.addBread(breadTaken);
                this.bakery.printBakery();
                System.out.println(breadTaken + " Bread added");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
