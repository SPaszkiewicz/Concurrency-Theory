package lab2.WritersReaders;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Library {
    private int writers = 0;
    private int readers = 0;
    private Lock lock = new ReentrantLock();
    final Condition writerUsing  = lock.newCondition();
    final Condition readerUsing = lock.newCondition();

    void addReader() throws InterruptedException {
        lock.lock();
        while (writers > 0) {
            writerUsing.await();
        }
        readers += 1;
        lock.unlock();
    }
    void removeReader() throws InterruptedException {
        lock.lock();
        readers -= 1;
        if (readers == 0) {
            readerUsing.signal();
        }
        lock.unlock();
    }
    void addWriter() throws InterruptedException {
        lock.lock();
        while (writers + readers > 0) {
            readerUsing.await();
        }
        writers += 1;
        lock.unlock();
    }
    void removeWriter() throws InterruptedException {
        lock.lock();
        writers -= 1;
        if (readers == 0) {
            writerUsing.signalAll();
        }
        readerUsing.signal();
        lock.unlock();
    }
}
