package lab6.ActiveObject.methods;

import lab6.ActiveObject.Buffor;
import lab6.ActiveObject.methods.IMethodRequest;

public class AddToBuffor implements IMethodRequest {

    public int portion;
    final Buffor buffor;

    public AddToBuffor(int portion, Buffor buffor) {
        this.portion = portion;
        this.buffor = buffor;
    }

    @Override
    public void call() {
        buffor.addResources(portion);
    }

    @Override
    public boolean guard() {
        return buffor.canAdd(portion);
    }
}
