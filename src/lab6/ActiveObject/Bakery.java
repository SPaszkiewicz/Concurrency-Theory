package lab6.ActiveObject;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bakery {
    private final int bufforSize;
    private int currentBuffor;
    private final Lock lockAdding = new ReentrantLock();
    private final Lock lockTaking = new ReentrantLock();
    private final Lock lockFirst = new ReentrantLock();
    final Condition firstInQueue = lockFirst.newCondition();

    public Bakery (int bufforSize) {
        this.bufforSize = bufforSize;
        this.currentBuffor = 0;
    }

    void addBread(int portion) throws InterruptedException {
        lockAdding.lock();
        lockFirst.lock();
        while (portion + this.currentBuffor > this.bufforSize) {
            firstInQueue.await();
        }
        this.currentBuffor += portion;
        firstInQueue.signal();
        lockFirst.unlock();
        lockAdding.unlock();
    }

    void takeBread(int portion) throws InterruptedException {
        lockTaking.lock();
        lockFirst.lock();
        while (this.currentBuffor - portion < 0) {
            firstInQueue.await();
        }
        this.currentBuffor -= portion;
        firstInQueue.signal();
        lockFirst.unlock();
        lockTaking.unlock();
    }
}