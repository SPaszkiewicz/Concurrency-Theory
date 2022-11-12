package lab6.ActiveObject.core;

import lab6.ActiveObject.methods.AddToBuffer;
import lab6.ActiveObject.methods.IMethodRequest;
import lab6.ActiveObject.methods.RemoveFromBuffor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler implements Runnable {
    private final ActivationQueue activationQueue;

    private final Queue<IMethodRequest> prioritizedQueue;
    private final Servant servant;
    private PrioritizedQueueState queueState = PrioritizedQueueState.EMPTY;
    private final ReentrantLock queueLock = new ReentrantLock();
    final Condition emptyQueue = queueLock.newCondition();

    public Scheduler(Servant servant) {
        this.activationQueue = new ActivationQueue();
        this.prioritizedQueue = new LinkedList<>();
        this.servant = servant;
    }

    private boolean areProducersBlocked(IMethodRequest method) {
        return method instanceof AddToBuffer && queueState == PrioritizedQueueState.PRODUCER;
    }

    private boolean areConsumersBlocked(IMethodRequest method) {
        return method instanceof RemoveFromBuffor && queueState == PrioritizedQueueState.CONSUMER;
    }

    public void enqueue(IMethodRequest method) throws InterruptedException {
        queueLock.lock();
        if (areConsumersBlocked(method) || areProducersBlocked(method)) {
            prioritizedQueue.add(method);
        } else {
            activationQueue.enqueue(method);
            emptyQueue.signal();
        }
        queueLock.unlock();
    }

    public void dispatch() throws InterruptedException {
        while (true) {
            this.queueLock.lock();
            while (this.activationQueue.isEmpty()) {
                this.emptyQueue.await();
            }
            IMethodRequest method;
            if (!prioritizedQueue.isEmpty() && prioritizedQueue.element().guard()) {
                method = this.prioritizedQueue.remove();
                if (prioritizedQueue.isEmpty()) {
                    queueState = PrioritizedQueueState.EMPTY;
                }
            } else {
                method = this.activationQueue.dequeue();
            }

            if (method.guard()) {
                method.call();
            } else {
                this.prioritizedQueue.add(method);
                if (method instanceof AddToBuffer) {
                    this.queueState = PrioritizedQueueState.PRODUCER;
                } else {
                    this.queueState = PrioritizedQueueState.CONSUMER;
                }
            }
            this.queueLock.unlock();
        }
    }

    @Override
    public void run() {
        try {
            dispatch();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
