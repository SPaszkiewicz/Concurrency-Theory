package lab2.customerProducer;

public class Bakery {
    private boolean isFull;
    private int bufforSize;
    private int currentBuffor;
    private boolean isEmpty;

    public void printBakery(){
        System.out.println(currentBuffor);
    }
    public Bakery (int bufforSize) {
        this.isFull = false;
        this.isEmpty = true;
        this.bufforSize = bufforSize;
        this.currentBuffor = 0;
    }

    synchronized void addBread(int portion) throws InterruptedException {
        while (isFull) {
            wait();
        }
        this.currentBuffor = Math.min(currentBuffor + portion, bufforSize);
        this.isEmpty = false;
        if (this.bufforSize == this.currentBuffor) {
            this.isFull = true;
        }
        notifyAll();
    }

    synchronized void takeBread(int portion) throws InterruptedException {
        while (isEmpty || this.currentBuffor - portion < 0) {
            wait();
        }
        this.currentBuffor -= portion;
        this.isFull = false;
        if (this.currentBuffor == 0) {
            this.isEmpty = true;
        }
        notifyAll();
    }
}
// synchronizacja na dwóch metodach