package lab9.Model;

import org.jcsp.lang.*;

public class Contributor implements CSProcess {
    private final One2OneChannelInt responseChannel;
    private final One2OneChannelInt requestChannel;
    private final int numOfPipes;
    public One2OneChannelInt[] consumersRequestPipes;
    public One2OneChannelInt[] consumersResponsePipes;
    public int operations = 0;

    public Contributor(One2OneChannelInt responseChannel, One2OneChannelInt requestChannel, int numOfPipes) {
        this.responseChannel = responseChannel;
        this.requestChannel = requestChannel;
        this.numOfPipes = numOfPipes;
        createPipes();
    }

    private void createPipes() {
        consumersRequestPipes = new One2OneChannelInt[numOfPipes];
        consumersResponsePipes = new One2OneChannelInt[numOfPipes];
        for (int i = 0; i < numOfPipes; i++) {
            consumersRequestPipes[i] = Channel.one2oneInt();
            consumersResponsePipes[i] = Channel.one2oneInt();
        }
    }

    @Override
    public void run() {
        int index, item;
        Guard[] guards = new Guard[numOfPipes];
        for (int i = 0; i < numOfPipes; i++) {
            guards[i] = consumersRequestPipes[i].in();
        }
        final Alternative alt = new Alternative(guards);
        while (true) {
            index = alt.select();
            consumersRequestPipes[index].in().read();

            requestChannel.out().write(1);
            item = responseChannel.in().read();

            consumersResponsePipes[index].out().write(item);
            operations++;
        }
    }
}
