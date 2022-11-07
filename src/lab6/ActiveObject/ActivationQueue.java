package lab6.ActiveObject;

import lab6.ActiveObject.methods.IMethodRequest;

import java.util.concurrent.SynchronousQueue;

public class ActivationQueue {
    private final SynchronousQueue<IMethodRequest> queue = new SynchronousQueue<>();

    public void enqueue(IMethodRequest method) throws InterruptedException {
        this.queue.put(method);
    }

    public IMethodRequest dequeue() throws InterruptedException {
     return this.queue.take();
    }
}
