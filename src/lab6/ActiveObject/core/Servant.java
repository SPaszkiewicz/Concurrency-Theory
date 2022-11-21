package lab6.ActiveObject.core;

import lab6.ActiveObject.TimeOrchiester;

public class Servant {
    private final Buffer buffer;
    public int addFinished;
    public int removeFinished;
    private int time;
    public Servant(Buffer buffer, int time) {
        this.buffer = buffer;
        this.addFinished = 0;
        this.removeFinished = 0;
        this.time = time;
    }

    public boolean canRemove(int portion) {
        return buffer.canRemove(portion);
    }

    public void removeResources(int portion) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        buffer.removeResources(portion);
//        System.out.println("Remove resources " + buffer.currentState);
        removeFinished += 1;
    }

    public void addResources(int portion) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        buffer.addResources(portion);
//        System.out.println("Adding resources " + buffer.currentState);
        addFinished += 1;
    }

    public boolean canAdd(int portion) {
        return buffer.canAdd(portion);
    }
}
