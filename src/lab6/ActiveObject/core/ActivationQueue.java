package lab6.ActiveObject.core;

import lab6.ActiveObject.methods.IMethodRequest;

import java.util.LinkedList;
import java.util.Queue;

public class ActivationQueue {
    private final Queue<IMethodRequest> queue = new LinkedList<>();

    public void enqueue(IMethodRequest method) throws InterruptedException {
        this.queue.add(method);
    }

    public IMethodRequest dequeue() throws InterruptedException {
     return this.queue.poll();
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
