package learn.java.threads.example4;

/**
 * Without the volatile keyword on the counters:
 * Threads finish counting very fast
 *
 * When adding the volatile keyword:
 * Threads take much longer time to finish
 *
 * When adding the 8 longs:
 * Performance is improved even with the volatile keyword
 */
public class VolatileExample {

    private static final int COUNTER = 1 << 28; // 2^28

    private final static class Pair {
        private volatile/*volatile*/ long c1;
        // private long l1, l2, l3, l4, l5, l6, l7, l8;
        private volatile/*volatile*/ long c2;
    }

    private static final Pair pair = new Pair();

    private static class CountRunnable implements Runnable {

        private final int counterNumber;

        public CountRunnable(int i) {
            this.counterNumber = i;
        }

        @Override
        public void run() {
            long start = System.nanoTime();

            if (this.counterNumber == 1 ){
                for (int i = 0; i < COUNTER; i++) {
                    pair.c1++;
                }
            } else {
                for (int i = 0; i < COUNTER; i++) {
                    pair.c2++;
                }
            }
            long end = System.nanoTime();
            System.out.println(Thread.currentThread().getName() + " Counting Took: " + (end-start) / 1000000000.0 + " sec");
            System.out.println(Thread.currentThread().getName() + " c1 + c2 = " + pair.c1+pair.c2);
        }

    }

    public static void main(String[] args) {
        System.out.println("Counting to: " + COUNTER);

        Thread t1 = new Thread(new CountRunnable(1));
        Thread t2 = new Thread(new CountRunnable(2));;

        t1.start();
        t2.start();
    }
}
