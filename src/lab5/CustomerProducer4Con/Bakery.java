package lab5.CustomerProducer4Con;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bakery {
    private final int bufforSize;
    private int currentBuffor;
    private final Lock lock = new ReentrantLock();
    final Condition breadAdding  = lock.newCondition();
    final Condition breadTaking  = lock.newCondition();
    private boolean firstAddingExist = false;
    private boolean firstTakingExist = false;
    final Condition firstAdding  = lock.newCondition();
    final Condition firstTaking  = lock.newCondition();

    public Bakery (int bufforSize) {
        this.bufforSize = bufforSize;
        this.currentBuffor = 0;
    }

    void addBread(int portion) throws InterruptedException {
        lock.lock();
        while (this.firstAddingExist) {
            firstAdding.await();
        }
        this.firstAddingExist = true;
        while (portion + this.currentBuffor > this.bufforSize ) {
            breadAdding.await();
        }
        this.currentBuffor += portion;
        this.firstAddingExist = false;
        firstAdding.signal();
        breadTaking.signal();
        lock.unlock();
    }

    void takeBread(int portion) throws InterruptedException {
        lock.lock();
        while (firstTakingExist) {
            firstTaking.await();
        }
        this.firstTakingExist = true;
        while (this.currentBuffor - portion < 0) {
            breadTaking.await();
        }
        this.currentBuffor -= portion;
        this.firstTakingExist = false;
        firstTaking.signal();
        breadAdding.signal();
        lock.unlock();
    }
}