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
        activationQueue.enqueue(method);
        emptyQueue.signal();
        queueLock.unlock();
    }

    public void dispatch() throws InterruptedException {
        if (!prioritizedQueue.isEmpty() && prioritizedQueue.element().guard()) {
            prioritizedQueue.element().call();
            if (prioritizedQueue.isEmpty()) {
                queueState = PrioritizedQueueState.EMPTY;
            }
            return;
        }
        this.queueLock.lock();
        while (this.activationQueue.isEmpty()) {
            this.emptyQueue.await();
        }
        IMethodRequest method = this.activationQueue.dequeue();
        this.queueLock.unlock();

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
    }

    @Override
    public void run() {
        try {
            while(true){
                dispatch();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
