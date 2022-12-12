package lab8.Threads;

import lab8.Model.ContributorNode;
import lab8.Stats;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements CSProcess, Stats {
    private final ArrayList<ContributorNode> channels;
    private final int numberOfChannels;
    private final int index;
    private int operations;

    public Consumer (ArrayList<ContributorNode> channels, ArrayList<CSProcess> activationList, int index) {
        this.channels = channels;
        this.numberOfChannels = channels.size();
        this.index = index;
        activationList.add(this);
    }
    public void run () {
        while(true) {
            int choice = randomPipe(0, numberOfChannels);
            channels.get(choice).informingChannel[index].out().write(1);
            int item = channels.get(choice).pushingChannel[index].in().read();
//            System.out.println("Received " + item);
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
