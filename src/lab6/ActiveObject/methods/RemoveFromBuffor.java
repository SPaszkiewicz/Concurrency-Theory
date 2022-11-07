package lab6.ActiveObject.methods;

import lab6.ActiveObject.Buffor;
import lab6.ActiveObject.methods.IMethodRequest;

public class RemoveFromBuffor implements IMethodRequest {

    public int portion;
    final Buffor buffor;

    public RemoveFromBuffor(int portion, Buffor buffor) {
        this.portion = portion;
        this.buffor = buffor;
    }

    @Override
    public void call() {
        buffor.removeResources(portion);
    }

    @Override
    public boolean guard() {
        return buffor.canRemove(portion);
    }
}
