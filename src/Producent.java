public class Producent implements Runnable {
    private Bakery bakery;
    public Producent (Bakery bakery) {
        this.bakery = bakery;
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
                this.bakery.addBread();
                this.bakery.printBakery();
                System.out.println("Bread added");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
