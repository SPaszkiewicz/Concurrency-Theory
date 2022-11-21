package lab6.ActiveObject;

import lab6.ActiveObject.threads.Customer;
import lab6.ActiveObject.threads.Producent;

import java.util.ArrayList;
import java.util.List;

public class TimeOrchiester {
    private final List<Producent> producerThreads;
    private final List<Customer> customerThreads;

    public boolean isFinished = false;

    private int[] timeStamps;


    public TimeOrchiester (List<Producent> producerThreads, List<Customer> customerThreads, int[] timeStamps) {
        this.customerThreads = customerThreads;
        this.producerThreads = producerThreads;
        this.timeStamps = timeStamps;
    }

    public int operationCounter() {
        return customerThreads.stream().map(Customer::getReceivedBuffer).reduce(0, Integer::sum) +
                producerThreads.stream().map(Producent::getReceivedBuffer).reduce(0, Integer::sum);
    }
    public void startProcessing() {
        List<Integer> results = new ArrayList<Integer>();
        List<List<String>> dataLines = new ArrayList<>();
        int startSum;
        int endSum;
        for(int timeSwamp : timeStamps) {
            startSum = operationCounter();
            try {
                Thread.sleep(timeSwamp);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            endSum = operationCounter();
            results.add(endSum - startSum);
        }
        System.out.println(results);
    }

}
