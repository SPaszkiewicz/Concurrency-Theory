package lab9;

import lab9.Model.Buffer;
import lab9.Model.Contributor;
import lab9.Model.Receiver;
import lab9.Threads.Consumer;
import lab9.Threads.Producer;
import org.jcsp.lang.CSProcess;

import java.util.ArrayList;

public class StatisticsGetter implements Runnable{
    private final CSProcess[] procs;

    public StatisticsGetter(CSProcess[] procs) {
        this.procs = procs;
    }

    public void getStats() {
        ArrayList<Consumer> consumers = new ArrayList<>();
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Receiver> receiverNodes = new ArrayList<>();
        ArrayList<Contributor> contributorNodes = new ArrayList<>();
        for (CSProcess proc: procs) {
            if (proc instanceof Consumer) {
                consumers.add((Consumer) proc);
            } else if (proc instanceof Producer) {
                producers.add((Producer) proc);
            } else if (proc instanceof Receiver) {
                receiverNodes.add((Receiver) proc);
            } else if (proc instanceof Contributor) {
                contributorNodes.add((Contributor) proc);
            } else if (proc instanceof Buffer) {
                System.out.println("Buffer operations: " + ((Buffer) proc).operations);
            }
        }
        System.out.println("Consumers operations: ");
        for (Consumer consumer : consumers) {
            System.out.println(consumer.getOperations());
        }
        System.out.println("Producer operations: ");
        for (Producer producer : producers) {
            System.out.println(producer.getOperations());
        }
        System.out.println("ReceiverNode operations: ");
        for (Receiver receiverNode : receiverNodes) {
            System.out.println(receiverNode.operations);
        }
        System.out.println("ContributorNode operations: ");
        for (Contributor contributorNode : contributorNodes) {
            System.out.println(contributorNode.operations);
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.getStats();
    }
}
