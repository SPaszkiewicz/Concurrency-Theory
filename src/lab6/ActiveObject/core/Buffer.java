package lab6.ActiveObject.core;

public class Buffer {
    public final int bufforSize;
    public int currentState;

    public Buffer(int bufforSize) {
        this.bufforSize = bufforSize;
        this.currentState = 0;
    }

    public boolean canAdd(int portion) {
        // System.out.println("Added to " + currentState + " state " + (currentState + portion <= bufforSize) + " " + portion);
        return currentState + portion <= bufforSize;
    }

    public boolean canRemove(int portion) {
        // System.out.println("Removed to " + currentState + " state " + (currentState - portion >= 0) + " " + portion);
        return currentState - portion >= 0;
    }

    public void removeResources(int portion) {
        currentState -= portion;
    }

    public void addResources(int portion) {
        currentState += portion;
    }
}
