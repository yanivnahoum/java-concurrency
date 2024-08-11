package learn.java.threads.example3;

import learn.java.threads.Utils;

import java.util.concurrent.atomic.AtomicInteger;

public class SlowCountingThreads {

    private static AtomicInteger atomicCounter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable incrementor = () -> {
            for (int i = 0; i < 500_000_000; i++) {
                atomicCounter.incrementAndGet();
            }
        };

        long start = Utils.now();
        Thread t1 = new Thread(incrementor);
        t1.start();

        Thread t2 = new Thread(incrementor);
        t2.start();

        t1.join();
        t2.join();

        Utils.elapsedFrom(start);
        System.out.println("Counter=" + atomicCounter);
    }
}
