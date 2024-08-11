package learn.java.threads.example3;

import learn.java.threads.Utils;

import java.util.concurrent.atomic.LongAdder;

public class FasterCountingThreads {

    private static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) throws InterruptedException {
        Runnable incrementor = () -> {
            for (int i = 0; i < 100_000_000; i++) {
                longAdder.increment();
            }
        };

        long start = Utils.now();
        Thread t1 = new Thread(incrementor);
        Thread t2 = new Thread(incrementor);
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        Utils.elapsedFrom(start);
        System.out.println("Counter=" + longAdder);
    }
}
