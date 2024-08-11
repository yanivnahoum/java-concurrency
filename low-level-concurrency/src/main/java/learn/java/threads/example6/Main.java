package learn.java.threads.example6;

import learn.java.threads.Utils;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        AtomicRefLocationUpdater atomicRefUpdater = new AtomicRefLocationUpdater();
        SyncLocationUpdater synchronisedUpdater = new SyncLocationUpdater();

        //---- Atomic Ref Updater ---//
        System.out.println("Starting Atomic Ref Updater");
        System.out.println("===========================");
        runThreads(atomicRefUpdater);

        //---- Synchronised Updater ---//
        System.out.println("Starting Synchronised Updater");
        System.out.println("=============================");
        runThreads(synchronisedUpdater);
    }

    private static void runThreads(Runnable runnable) throws InterruptedException {
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        long start = Utils.now();
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        Utils.elapsedFrom(start);
    }
}
