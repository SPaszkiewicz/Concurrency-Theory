package lab3.AsyncCustomerProducer.Version1;


public class Customer implements Runnable{
    private final Bakery bakery;
    private final int maxFoodPortion;
    public Customer (Bakery bakery, int maxFoodPortion) {
        this.bakery = bakery;
        this.maxFoodPortion = maxFoodPortion;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.bakery.takeBread();
                System.out.println("Bread taken");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Stop eating");
        }
    }
}

