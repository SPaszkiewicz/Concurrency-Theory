package lab2.WritersReaders;

import lab2.customerProducer.Bakery;
import lab2.customerProducer.Customer;
import lab2.customerProducer.Producent;

public class Main {
    static int numberOfReaders = 5;
    static int numberOfWriters = 1;

    public static void main(String[] args) throws InterruptedException {
        Library library = new Library();
        for (int i = 0; i < numberOfReaders; i++) {
            new Thread(new Reader(library)).start();
        }
        for (int i = 0; i < numberOfWriters; i++) {
            new Thread(new Writer(library)).start();
        }
    }
}
