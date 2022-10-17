package lab3.CustomerProducer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bakery {
    private boolean isFull;
    private int bufforSize;
    private int currentBuffor;
    private boolean isEmpty;
    private Lock lock = new ReentrantLock();
    final Condition breadAdding  = lock.newCondition();
    final Condition breadTaking  = lock.newCondition();

    public void printBakery(){
        System.out.println(currentBuffor);
    }
    public Bakery (int bufforSize) {
        this.isFull = false;
        this.isEmpty = true;
        this.bufforSize = bufforSize;
        this.currentBuffor = 0;
    }

    void addBread(int portion) throws InterruptedException {
        lock.lock();
        while (isFull || portion + this.currentBuffor > this.bufforSize ) {
            breadAdding.await();
        }
        this.isEmpty = false;
        if (this.bufforSize == this.currentBuffor) {
            this.isFull = true;
        }
        breadTaking.signal();
        lock.unlock();
    }

    void takeBread(int portion) throws InterruptedException {
        lock.lock();
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