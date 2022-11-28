package lab7.jcsp_a;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess
{
    One2OneChannelInt channel;
    public Consumer (final One2OneChannelInt in) {
        this.channel = in;
    }
    public void run ()
    { int item = channel.in().read();
        System.out.println(item);
    }
}
