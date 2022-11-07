package lab6.ActiveObject;

public class Buffor {
    public final int bufforSize;
    public int currentState;

    public Buffor(int bufforSize) {
        this.bufforSize = bufforSize;
        this.currentState = 0;
    }

    public boolean canAdd(int portion) {
     return currentState + portion <= bufforSize;
    }

    public boolean canRemove(int portion) {
        return currentState - portion >= 0;
    }

    public void removeResources(int portion) {
        currentState -= portion;
    }

    public void addResources(int portion) {
        currentState += portion;
    }
}
