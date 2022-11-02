package lab4.CustomerProducerHasWaiters;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bakery {
    private boolean isFull;
    private final int bufforSize;
    private int currentBuffor;
    private boolean isEmpty;
    public final ReentrantLock lock = new ReentrantLock();
    final Condition breadAdding  = lock.newCondition();
    final Condition breadTaking  = lock.newCondition();
    final Condition firstAdding  = lock.newCondition();
    final Condition firstTaking  = lock.newCondition();

    public void printBakery(){
        System.out.println(currentBuffor);
    }
    public Bakery (int bufforSize) {
        this.isFull = false;
        this.isEmpty = true;
        this.bufforSize = bufforSize;
        this.currentBuffor = 0;
    }

    public void addBread(int portion) throws InterruptedException {
        lock.lock();
        while (lock.hasWaiters(breadAdding)) {
            firstAdding.await();
        }
        while (isFull || portion + this.currentBuffor > this.bufforSize ) {
            breadAdding.await();
        }
        this.isEmpty = false;
        this.currentBuffor += portion;
        if (this.bufforSize == this.currentBuffor) {
            this.isFull = true;
        }
        firstAdding.signal();
        breadTaking.signal();
        lock.unlock();
    }

    void takeBread(int portion) throws InterruptedException {
        lock.lock();
        while (lock.hasWaiters(breadTaking)) {
            firstTaking.await();
        }
        while (isEmpty || this.currentBuffor - portion < 0) {
            breadTaking.await();
        }
        this.currentBuffor -= portion;
        this.isFull = false;
        if (this.currentBuffor == 0) {
            this.isEmpty = true;
        }
        firstTaking.signal();
        breadAdding.signal();
        lock.unlock();
    }
}