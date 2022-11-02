package lab4.CustomerProducerHasWaiters;

public class Timer {
    private boolean isStopped;
    private int time;
    private final Bakery bakery;
    public Timer(int time, Bakery bakery) {
        this.time = time;
        this.bakery = bakery;
        isStopped = false;
    }

    public void startTime() throws InterruptedException {
        Thread.sleep(this.time);
        this.isStopped = true;
    }

    public boolean isStopped() {
        return this.isStopped;

    }
}
