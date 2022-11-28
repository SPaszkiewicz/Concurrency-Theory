package lab7.jcsp_a;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    One2OneChannelInt channel;
    public Producer(final One2OneChannelInt out) {
        this.channel = out;
    }
    public void run () {
        int item = (int)(Math.random()*100)+1;
        channel.out().write(item);
    }
}