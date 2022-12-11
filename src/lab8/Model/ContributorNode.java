package lab8.Model;

import org.jcsp.lang.*;

import java.util.ArrayList;

public class ContributorNode implements CSProcess {
    private final int numOfLayers;
    private ContributorNode upperContributor;
    private ContributorNode lowerContributor;
    public One2OneChannelInt forwardChannel = Channel.one2oneInt();
    public One2OneChannelInt pushingChannel;
    public ContributorNode(
            int numOfLayers,
            ArrayList<ContributorNode> contributorEndpoints,
            ArrayList<CSProcess> activationList
    ) {
        this.numOfLayers = numOfLayers;
        activationList.add(this);

        if (numOfLayers <= 0) {
            pushingChannel = Channel.one2oneInt();
            contributorEndpoints.add(this);
        } else {
            this.upperContributor = new ContributorNode(numOfLayers - 1, contributorEndpoints, activationList);
            this.lowerContributor = new ContributorNode(numOfLayers - 1, contributorEndpoints, activationList);
        }
    }

    @Override
    public void run() {
        int item;
        PipeDirection state;

        if (numOfLayers <= 0) {
            state = PipeDirection.PUSH;
        } else {
            state = PipeDirection.UPPER;
        }

        if (state == PipeDirection.PUSH) {
            while (true) {
                item = forwardChannel.in().read();
                pushingChannel.out().write(item);
            }
        }

        while (true) {
            item = forwardChannel.in().read();
            if (state == PipeDirection.UPPER) {
                upperContributor.forwardChannel.out().write(item);
            } else {
                lowerContributor.forwardChannel.out().write(item);
            }
            state = state.switchDirection();
        }
    }
}
