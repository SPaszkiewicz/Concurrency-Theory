package lab6.ActiveObject.core;

public class Servant {
    private final Buffer buffer;

    public Servant(Buffer buffer) {
        this.buffer = buffer;
    }
    public boolean canRemove(int portion) {
        return buffer.canRemove(portion);
    }

    public void removeResources(int portion) {
        buffer.removeResources(portion);
        System.out.println("Remove resources " + buffer.currentState);
    }

    public void addResources(int portion) {
        buffer.addResources(portion);
        System.out.println("Adding resources " + buffer.currentState);
    }

    public boolean canAdd(int portion) {
        return buffer.canAdd(portion);
    }
}
