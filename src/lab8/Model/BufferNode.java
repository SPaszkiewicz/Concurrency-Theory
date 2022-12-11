package lab8.Model;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;

import java.util.ArrayList;

public class BufferNode implements CSProcess {
    private final int numOfConsumers;
    private final int numOfProducers;
    private final int nodePerConsumers;
    private final int nodePerProducers;
    private ReceiverNode upperReceiver;
    private ReceiverNode lowerReceiver;
    private ContributorNode upperContributor;
    private ContributorNode lowerContributor;
    private final ArrayList<ReceiverNode> receiverEndpoints = new ArrayList<>();
    private final ArrayList<ContributorNode> contributorEndpoints = new ArrayList<>();

    public BufferNode(
            int numOfConsumers,
            int numOfProducers,
            int nodePerConsumers,
            int nodePerProducers,
            ArrayList<CSProcess> activationList
    )  {
        this.numOfConsumers = numOfConsumers;
        this.numOfProducers = numOfProducers;
        this.nodePerConsumers = nodePerConsumers;
        this.nodePerProducers = nodePerProducers;
        activationList.add(this);
        buildReceiversBranch(activationList);
        buildContributorsBranch(activationList);
        System.out.println(contributorEndpoints);
        System.out.println(receiverEndpoints);
    }

    public void buildReceiversBranch(ArrayList<CSProcess> activationList) {
        double requiredNodes = Math.ceil((double) numOfProducers / nodePerProducers);
        int numberOfLayers = base2Log(requiredNodes);
        upperReceiver = new ReceiverNode(numberOfLayers - 1, receiverEndpoints, activationList, numOfProducers);
        lowerReceiver = new ReceiverNode(numberOfLayers - 1, receiverEndpoints, activationList, numOfProducers);
    }

    public void buildContributorsBranch(ArrayList<CSProcess> activationList) {
        double requiredNodes = Math.ceil((double) numOfConsumers / nodePerConsumers);
        int numberOfLayers = base2Log(requiredNodes);
        upperContributor = new ContributorNode(numberOfLayers - 1, contributorEndpoints, activationList, numOfConsumers);
        lowerContributor = new ContributorNode(numberOfLayers - 1, contributorEndpoints, activationList, numOfConsumers);

    }

    private int base2Log(double value) {
        return (int) Math.ceil(Math.log(value) / Math.log(2.0));
    }

    public ArrayList<ReceiverNode> getReceiverEndpoints() {
        return receiverEndpoints;
    }

    public ArrayList<ContributorNode> getContributorEndpoints() {
        return contributorEndpoints;
    }

    @Override
    public void run() {
        final Guard[] guards = {upperReceiver.forwardChannel.in(), lowerReceiver.forwardChannel.in()};
        final Alternative alt = new Alternative(guards);
        int index, item;
        PipeDirection state = PipeDirection.UPPER;
        while (true) {
            index = alt.select();
            switch (index) {
                case 0 -> {
                    item = upperReceiver.forwardChannel.in().read();
                    if (state == PipeDirection.UPPER) {
                        upperContributor.forwardChannel.out().write(item);
                    } else {
                        lowerContributor.forwardChannel.out().write(item);
                    }
                    state = state.switchDirection();
                }
                case 1 -> {
                    item = lowerReceiver.forwardChannel.in().read();
                    if (state == PipeDirection.UPPER) {
                        upperContributor.forwardChannel.out().write(item);
                    } else {
                        lowerContributor.forwardChannel.out().write(item);
                    }
                    state = state.switchDirection();
                }
            }
        }
    }
}
