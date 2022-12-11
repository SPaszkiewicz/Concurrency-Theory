package lab8.Model;

import org.jcsp.lang.*;

import java.util.ArrayList;

public class ReceiverNode implements CSProcess {
    private final int numOfLayers;
    private ReceiverNode upperReceiver;
    private ReceiverNode lowerReceiver;

    public One2OneChannelInt listeningChannel;

    public One2OneChannelInt forwardChannel = Channel.one2oneInt();

    public ReceiverNode(
            int numOfLayers,
            ArrayList<ReceiverNode> receiverEndpoints,
            ArrayList<CSProcess> activationList
    ) {
        this.numOfLayers = numOfLayers;
        activationList.add(this);

        if (numOfLayers <= 0) {
            listeningChannel = Channel.one2oneInt();
            receiverEndpoints.add(this);
        } else {
            this.upperReceiver = new ReceiverNode(numOfLayers - 1, receiverEndpoints, activationList);
            this.lowerReceiver = new ReceiverNode(numOfLayers - 1, receiverEndpoints, activationList);
        }
    }

    @Override
    public void run() {
        Guard[] guards;
        int index, item;
        if (listeningChannel != null) {
            while (true) {
                item = listeningChannel.in().read();
                forwardChannel.out().write(item);
            }
        } else {
            guards = new Guard[]{upperReceiver.forwardChannel.in(), lowerReceiver.forwardChannel.in()};
            final Alternative alt = new Alternative(guards);
            while (true) {
                index = alt.select();
                switch (index) {
                    case 0 -> {
                        item = upperReceiver.forwardChannel.in().read();
                        forwardChannel.out().write(item);
                    }
                    case 1 -> {
                        item = lowerReceiver.forwardChannel.in().read();
                        forwardChannel.out().write(item);
                    }
                }
            }
        }
    }
}
