package lab2.customerProducer;

public class Customer implements Runnable{
    private Bakery bakery;
    private int maxFoodPortion;
    public Customer (Bakery bakery, int maxFoodPortion) {
        this.bakery = bakery;
        this.maxFoodPortion = maxFoodPortion;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int breadTaken = Main.randomPortion(1, maxFoodPortion);
                this.bakery.takeBread(breadTaken);
                System.out.println(breadTaken + " Bread taken");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(1000);
                System.out.println("Stop eating");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
