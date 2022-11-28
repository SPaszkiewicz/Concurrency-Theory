package lab7.jcsp_a;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;

public final class Main
{
    public static void main (String[] args) {
        final One2OneChannelInt channel = Channel.one2oneInt();
        CSProcess[] procList = { new Producer(channel), new Consumer(channel) };
        Parallel par = new Parallel(procList);
        par.run();
    }
}