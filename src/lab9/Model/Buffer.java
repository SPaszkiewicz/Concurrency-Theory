package lab9.Model;

import org.jcsp.lang.*;

import java.util.ArrayList;

public class Buffer implements  CSProcess{

    private One2OneChannelInt[] receiversChannels;
    private One2OneChannelInt[] contributorsChannels;
    private One2OneChannelInt[] requestChannels;

    private int numOfReceivers;
    private int numOfContributors;

    public ArrayList<Receiver> receivers = new ArrayList<>();
    public ArrayList<Contributor> contributors = new ArrayList<>();
    public int operations = 0;

    public Buffer(
            int numOfConsumers,
            int numOfProducers,
            int nodePerConsumers,
            int nodePerProducers,
            ArrayList<CSProcess> activationList
    ) {
        activationList.add(this);
        this.numOfReceivers = numOfProducers/nodePerProducers;
        this.numOfContributors = numOfConsumers/nodePerConsumers;
        createReceivingPipes(numOfReceivers, numOfProducers, activationList);
        createContributorPipes(numOfContributors, numOfConsumers, activationList);
    }

    private void createReceivingPipes(int numOfReceivers, int numOfProducers, ArrayList<CSProcess> activationList) {
        receiversChannels = new One2OneChannelInt[numOfReceivers];
        Receiver receiver;
        for (int i = 0; i < numOfReceivers; i++) {
            receiversChannels[i] = Channel.one2oneInt();
            receiver = new Receiver(numOfProducers, receiversChannels[i]);
            receivers.add(receiver);
            activationList.add(receiver);
        }
    }

    private void createContributorPipes(int numOfContributors, int numOfConsumers,ArrayList<CSProcess> activationList) {
        contributorsChannels = new One2OneChannelInt[numOfContributors];
        requestChannels = new One2OneChannelInt[numOfContributors];
        Contributor contributor;
        for (int i = 0; i < numOfContributors; i++) {
            contributorsChannels[i] = Channel.one2oneInt();
            requestChannels[i] = Channel.one2oneInt();
            contributor = new Contributor(contributorsChannels[i], requestChannels[i], numOfConsumers);
            contributors.add(contributor);
            activationList.add(contributor);
        }
    }

    @Override
    public void run() {
        int contributorIndex, receiverIndex, item;
        Guard[] receiversGuards = new Guard[numOfReceivers];
        Guard[] contributorsGuards = new Guard[numOfContributors];
        for (int i = 0; i < numOfReceivers; i++) {
            receiversGuards[i] = receiversChannels[i].in();
        }
        for (int i = 0; i < numOfReceivers; i++) {
            contributorsGuards[i] = requestChannels[i].in();
        }
        final Alternative receiversAlt = new Alternative(receiversGuards);
        final Alternative contributorsAlt = new Alternative(contributorsGuards);
        while (true) {
            receiverIndex = receiversAlt.select();
            item = receiversChannels[receiverIndex].in().read();

            contributorIndex = contributorsAlt.select();
            requestChannels[contributorIndex].in().read();
            contributorsChannels[contributorIndex].out().write(item);
            operations++;
        }
    }
}
