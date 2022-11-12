package lab6.ActiveObject;

import lab6.ActiveObject.core.Buffer;
import lab6.ActiveObject.core.Future;
import lab6.ActiveObject.core.Scheduler;
import lab6.ActiveObject.core.Servant;
import lab6.ActiveObject.methods.AddToBuffer;
import lab6.ActiveObject.methods.RemoveFromBuffor;

public class Proxy {
    public final Scheduler scheduler;
    private final Buffer buffer;

    private final Servant servant;

    private final Thread thread;

    public Proxy(int bufferSize) {
        this.buffer = new Buffer(bufferSize);
        this.servant = new Servant(buffer);
        this.scheduler = new Scheduler(servant);
        this.thread = new Thread(scheduler);
        this.thread.start();
    }

    public Future add(int portion) throws InterruptedException {
        Future future = new Future();
        this.scheduler.enqueue(new AddToBuffer(portion, this.servant, future));
        return future;
    }

    public Future remove(int portion) throws InterruptedException {
        Future future = new Future();
        this.scheduler.enqueue(new RemoveFromBuffor(portion, this.servant, future));
        return future;
    }

}
