package lab8;

import org.jcsp.lang.One2OneChannelInt;

import java.util.concurrent.ThreadLocalRandom;

public class Consumer {
    private final One2OneChannelInt[] channels;
    private final int numberOfChannels;

    public Consumer (final One2OneChannelInt[] in, final int numberOfChannels) {
        this.channels = in;
        this.numberOfChannels = numberOfChannels;
    }
    public void run () {
        while(true) {
            int choice = randomPipe(0, numberOfChannels - 1);
            int item = channels[choice].in().read();
        }
    }

    private int randomPipe(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
