package learn.java.threads.example3;

import learn.java.threads.Utils;

public class IncorrectCountingThreads {

    private static /*volatile*/ int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable incrementor = () -> {
            for (int i = 0; i < 100_000_000; i++) {
                counter++;
            }
        };

        long start = Utils.now();
        Thread t1 = new Thread(incrementor);
        t1.start();

        Thread t2 = new Thread(incrementor);
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Counter =" + counter);
        Utils.elapsedFrom(start);
    }
}
