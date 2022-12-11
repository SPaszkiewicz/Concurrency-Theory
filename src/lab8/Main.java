package lab8;


import lab8.Model.BufferNode;
import lab8.Threads.Consumer;
import lab8.Threads.Producer;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;

import java.util.ArrayList;

public class Main {
    static int numOfProducers = 32;
    static int numOfConsumers = 32;
    static int nodePerConsumers = 1;
    static int nodePerProducers = 1;
    public static void main (String[] args) {
        ArrayList<CSProcess> activationList = new ArrayList<>();
        BufferNode buffer = new BufferNode(numOfProducers, numOfConsumers, nodePerConsumers, nodePerProducers, activationList);
        for (int i = 0; i < numOfProducers; i++) {
            new Consumer(buffer.getContributorEndpoints(), activationList, i);
        }
        for (int i = 0; i < numOfConsumers; i++) {
            new Producer(buffer.getReceiverEndpoints(), activationList, i);
        }
        CSProcess[] procList = new CSProcess[activationList.size()];
        activationList.toArray(procList);
        Parallel par = new Parallel(procList);
        par.run();
    }
}
