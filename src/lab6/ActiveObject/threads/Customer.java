package lab6.ActiveObject.threads;

import lab6.ActiveObject.core.Future;
import lab6.ActiveObject.Proxy;

import static lab3.CustomerProducer.Main.randomPortion;

public class Customer implements Runnable{
    private final Proxy proxy;
    private final int maxFoodPortion;
    private final int index;
    private Future task = null;
    private int receivedBuffer;
    public Customer (Proxy proxy, int maxFoodPortion, int index) {
        this.proxy = proxy;
        this.maxFoodPortion = maxFoodPortion;
        this.receivedBuffer = 0;
        this.index = index;
    }

    @Override
    public void run() {
        while (true) {
            if (task == null) {
                try {
                    int portion = randomPortion(1, maxFoodPortion);
                    this.task = this.proxy.remove(portion);
                    this.receivedBuffer++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else{
                boolean isDone = this.task.isDone();
                if (isDone) {
                    this.task = null;
                }
            }
        }
    }

    public int getReceivedBuffer() {
        return receivedBuffer;
    }
}

