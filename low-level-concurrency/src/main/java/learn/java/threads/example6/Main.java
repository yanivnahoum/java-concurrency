package learn.java.threads.example6;

import learn.java.threads.Utils;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        AtomicRefLocationUpdater atomicRefUpdater = new AtomicRefLocationUpdater();
        SyncLocationUpdater synchronisedUpdater = new SyncLocationUpdater();

        //---- Synchronised Updater ---//
        runThreads(synchronisedUpdater, "SYNCHRONIZED");

        //---- Atomic Ref Updater ---//
        runThreads(atomicRefUpdater, "ATOMIC REF");
    }

    private static void runThreads(Runnable runnable, String mode) throws InterruptedException {
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        System.out.println("Running 2 updating threads in mode: " + mode);
        System.out.println("========================================================");
        long start = Utils.now();
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Finished Current run in mode: " + mode);
        Utils.elapsedFrom(start);
        System.out.println("========================================================");
        System.out.println();
        System.out.println();
    }
}
