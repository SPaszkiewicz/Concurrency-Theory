package lab3.AsyncCustomerProducer.Version1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static lab3.CustomerProducer.Main.randomPortion;

public class Bakery {
    private boolean isFull;
    final private int bufforSize;
    private int currentBuffor;
    private boolean isEmpty;
    private final int maxFoodPortion;
    private final Lock lock = new ReentrantLock();
    final Condition breadAdding  = lock.newCondition();
    final Condition breadTaking  = lock.newCondition();

    public void printBakery(){
        System.out.println(currentBuffor);
    }
    public Bakery (int bufforSize, int maxFoodPortion) {
        this.isFull = false;
        this.isEmpty = true;
        this.bufforSize = bufforSize;
        this.currentBuffor = 0;
        this.maxFoodPortion = maxFoodPortion;
    }

    void addBread() throws InterruptedException {
        lock.lock();
        int portion = randomPortion(0, maxFoodPortion);
        while (isFull) {
            breadAdding.await();
        }
        if ( portion + this.currentBuffor <= this.bufforSize ) {
            this.isEmpty = false;
            this.currentBuffor += portion;
            if (this.bufforSize == this.currentBuffor) {
                this.isFull = true;
            }
            breadTaking.signal();
        }
        lock.unlock();
    }

    void takeBread() throws InterruptedException {
        lock.lock();
        int portion = randomPortion(0, maxFoodPortion);
        while (isEmpty || this.currentBuffor - portion < 0) {
            breadTaking.await();
        }
        this.currentBuffor -= portion;
        this.isFull = false;
        if (this.currentBuffor == 0) {
            this.isEmpty = true;
        }
        breadAdding.signal();
        lock.unlock();
    }
}