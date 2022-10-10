import java.sql.Array;

public class Main {
    static int numberOfThreads = 5;
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Threader threaderDecrease = new Threader(0, counter);
        Threader threaderIncrease = new Threader(1, counter);
        Thread threadIncrease = new Thread(threaderIncrease);
        Thread threadDecrease = new Thread(threaderDecrease);
        threadIncrease.start();
        threadDecrease.start();
        threadIncrease.join();
        threadDecrease.join();
        counter.show();
    }
}
