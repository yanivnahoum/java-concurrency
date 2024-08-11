package learn.java.threads.example6;

import learn.java.threads.Utils;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Plane atomicRefPlane = new AtomicRefPlane();
        Plane syncPlane = new SyncPlane();

        //---- Synchronised Update/Read ---//
        LocationUpdater synchronisedUpdater = new LocationUpdater(syncPlane);
        LocationReader synchronisedReader = new LocationReader(syncPlane);
        runThreads(synchronisedUpdater, synchronisedReader, "SYNCHRONIZED");

        //---- Atomic Ref Update/Read ---//
        LocationUpdater atomicRefUpdater = new LocationUpdater(atomicRefPlane);
        LocationReader atomicRefReader = new LocationReader(atomicRefPlane);
        runThreads(atomicRefUpdater, atomicRefReader, "ATOMIC REF");
    }

    private static void runThreads(Runnable updater, Runnable reader, String mode) throws InterruptedException {
        Thread t1 = new Thread(updater);
        Thread t2 = new Thread(reader);
        Thread t3 = new Thread(reader);
        Thread t4 = new Thread(reader);

        System.out.println("Running 1 updating threads and 3 readers in mode: " + mode);
        System.out.println("================================================================");
        long start = Utils.now();
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("Finished Current run in mode: " + mode);
        Utils.elapsedFrom(start);
        System.out.println("========================================================");
        System.out.println();
        System.out.println();
    }
}
