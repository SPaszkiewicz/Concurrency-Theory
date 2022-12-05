package lab8;

import lab7.jcsp_a.Consumer;
import lab7.jcsp_a.Producer;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;

public class Main {
    public static void main (String[] args) {
        final One2OneChannelInt channel = Channel.one2oneInt();
        CSProcess[] procList = { new Producer(channel), new Consumer(channel) };
        Parallel par = new Parallel(procList);
        par.run();
    }
}
