package lab9.Threads;

import lab9.Model.Receiver;
import lab8.Stats;
import org.jcsp.lang.CSProcess;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Producer implements CSProcess, Stats {
    private ArrayList<Receiver> channels;
    private final int numberOfChannels;
    private final int index;
    private int operations = 0;

    public Producer (ArrayList<Receiver> channels, ArrayList<CSProcess> activationList, int index) {
        this.channels = channels;
        this.numberOfChannels = channels.size();
        this.index = index;
        activationList.add(this);
    }
    public void run () {
        while(true) {
            int choice = randomPipe(0, numberOfChannels);
            channels.get(choice).producersPipes[index].out().write(1);
            operations += 1;
        }
    }

    private int randomPipe(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public int getOperations() {
        return operations;
    }
}
