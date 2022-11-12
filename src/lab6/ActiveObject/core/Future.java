package lab6.ActiveObject.core;

public class Future {
    private boolean isDone = false;

    public synchronized boolean isDone() {
        return this.isDone;
    }

    public synchronized void markDone () {
        this.isDone = true;
    }
}
