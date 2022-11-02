package lab5.CustomerProducerLocks;

import java.util.Arrays;
import java.util.List;

public class TimeOrchiester {
    private final List<Thread> producerThreads;
    private final List<Thread> customerThreads;

    private int[] timeStamps;

    public TimeOrchiester (List<Thread> producerThreads, List<Thread> customerThreads, int[] timeStamps) {
        this.customerThreads = customerThreads;
        this.producerThreads = producerThreads;
        this.timeStamps = timeStamps;
    }

    public void startProcessing() {
        int startSum;
        int endSum;
        for(int timeSwamp : timeStamps) {
            startSum = 0;
            endSum = 0;
        }
    }


}
