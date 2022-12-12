package lab8;

import lab7.jscp_b.Buffer;
import lab8.Model.BufferNode;
import lab8.Model.ContributorNode;
import lab8.Model.ReceiverNode;
import lab8.Threads.Consumer;
import lab8.Threads.Producer;
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
        ArrayList<ReceiverNode> receiverNodes = new ArrayList<>();
        ArrayList<ContributorNode> contributorNodes = new ArrayList<>();
        for (CSProcess proc: procs) {
            if (proc instanceof Consumer) {
                consumers.add((Consumer) proc);
            } else if (proc instanceof Producer) {
                producers.add((Producer) proc);
            } else if (proc instanceof ReceiverNode) {
                receiverNodes.add((ReceiverNode) proc);
            } else if (proc instanceof ContributorNode) {
                contributorNodes.add((ContributorNode) proc);
            } else if (proc instanceof BufferNode) {
                System.out.println("Buffer operations: " + ((BufferNode) proc).getOperations());
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
        for (ReceiverNode receiverNode : receiverNodes) {
            System.out.println(receiverNode.getOperations());
        }
        System.out.println("ContributorNode operations: ");
        for (ContributorNode contributorNode : contributorNodes) {
            System.out.println(contributorNode.getOperations());
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.getStats();
    }
}
