public class Customer implements Runnable{
    private Bakery bakery;
    public Customer (Bakery bakery) {
        this.bakery = bakery;
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
            try {
                Thread.sleep(500);
                System.out.println("Stop eating");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
