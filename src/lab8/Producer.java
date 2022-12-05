package lab8;

import org.jcsp.lang.One2OneChannelInt;

import java.util.concurrent.ThreadLocalRandom;

public class Producer {
        private final One2OneChannelInt[] channels;
        private final int numberOfChannels;

    public Producer (final One2OneChannelInt[] out, final int numberOfChannels) {
        this.channels = out;
        this.numberOfChannels = numberOfChannels;
    }
    public void run () {
        while(true) {
            int choice = randomPipe(0, numberOfChannels - 1);
            channels[choice].out().write(1);
        }
    }

    private int randomPipe(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
