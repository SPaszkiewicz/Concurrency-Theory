package lab8.Model;

import org.jcsp.lang.*;

import java.util.ArrayList;

public class ContributorNode implements CSProcess {
    private final int numOfLayers;
    private ContributorNode upperContributor;
    private ContributorNode lowerContributor;
    public One2OneChannelInt forwardChannel = Channel.one2oneInt();
    public One2OneChannelInt[] pushingChannel;
    public One2OneChannelInt[] informingChannel;

    public ContributorNode(
            int numOfLayers,
            ArrayList<ContributorNode> contributorEndpoints,
            ArrayList<CSProcess> activationList,
            int numOfConsumers
    ) {
        this.numOfLayers = numOfLayers;
        activationList.add(this);

        if (numOfLayers <= 0) {
            contributorEndpoints.add(this);
            pushingChannel = new One2OneChannelInt[numOfConsumers];
            informingChannel = new One2OneChannelInt[numOfConsumers];
            for (int i = 0; i < numOfConsumers; i++) {
                pushingChannel[i] = Channel.one2oneInt();
                informingChannel[i] = Channel.one2oneInt();
            }
        } else {
            this.upperContributor = new ContributorNode(numOfLayers - 1, contributorEndpoints, activationList, numOfConsumers);
            this.lowerContributor = new ContributorNode(numOfLayers - 1, contributorEndpoints, activationList, numOfConsumers);
        }
    }

    @Override
    public void run() {
        PipeDirection state;
        int item;
        if (numOfLayers <= 0) {
            state = PipeDirection.PUSH;
        } else {
            state = PipeDirection.UPPER;
        }
        if (state == PipeDirection.PUSH) {
            int index;
            Guard[] guards = new Guard[informingChannel.length];
            for (int i = 0; i < informingChannel.length; i++) {
                guards[i] = informingChannel[i].in();
            }
            final Alternative alt = new Alternative(guards);
            while (true) {
                item = forwardChannel.in().read();
                index = alt.select();
                informingChannel[index].in().read();
                pushingChannel[index].out().write(item);
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
