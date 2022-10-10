public class Threader implements Runnable {
    static int numberOfLoops = 100000;
    private int type;
    private final Counter counter;

    public Threader(int type, Counter counter) {
        this.type = type;
        this.counter = counter;
    }

    public void incrementator() {
        for (int i = 0; i < numberOfLoops; i++) {
            this.counter.increment();
        }
    }

    public void decrementator() {
        for (int i = 0; i < numberOfLoops; i++) {
            this.counter.decreament();
        }
    }

    @Override
    public void run() {
        if (this.type == 0){
            this.decrementator();
        } else {
            this.incrementator();
        }
    }
}
