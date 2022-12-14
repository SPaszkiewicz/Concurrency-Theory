package lab9.Model;

import org.jcsp.lang.*;

public class Receiver implements CSProcess {
    private final int numOfPipes;
    private final One2OneChannelInt forwardChannel;

    public One2OneChannelInt[] producersPipes;

    public int operations = 0;
    public Receiver(int numOfPipes, One2OneChannelInt forwardChannel) {
        this.numOfPipes = numOfPipes;
        this.forwardChannel = forwardChannel;
        createPipes();
    }

    private void createPipes() {
        producersPipes = new One2OneChannelInt[numOfPipes];
        for (int i = 0; i < numOfPipes; i++) {
            producersPipes[i] = Channel.one2oneInt();
        }
    }

    @Override
    public void run() {
        int index, item;
        Guard[] guards = new Guard[numOfPipes];
        for (int i = 0; i < numOfPipes; i++) {
            guards[i] = producersPipes[i].in();
        }
        final Alternative alt = new Alternative(guards);
        while (true) {
            index = alt.select();
            item = producersPipes[index].in().read();
            forwardChannel.out().write(item);
            operations++;
        }
    }
}
