package lab9;

import lab9.Model.Buffer;

import lab9.Threads.Consumer;
import lab9.Threads.Producer;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Parallel;

import java.util.ArrayList;

public class Main {
    static int numOfProducers = 20;
    static int numOfConsumers = 20;
    static int nodePerConsumers = 5;
    static int nodePerProducers = 5;
    public static void main (String[] args) {
        ArrayList<CSProcess> activationList = new ArrayList<>();
        Buffer buffer = new Buffer(numOfProducers, numOfConsumers, nodePerConsumers, nodePerProducers, activationList);
        for (int i = 0; i < numOfProducers; i++) {
            new Consumer(buffer.contributors, activationList, i);
        }
        for (int i = 0; i < numOfConsumers; i++) {
            new Producer(buffer.receivers, activationList, i);
        }
        CSProcess[] procList = new CSProcess[activationList.size()];
        activationList.toArray(procList);
        Parallel par = new Parallel(procList);
        new Thread(new StatisticsGetter(procList)).start();
        par.run();
    }
}
