package learn.java.threads.example2;

/**
 * How to interrupt a thread during its job
 */
public class InterruptingThreads {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("-- main thread starts --");

        // Start an infinite task
        Thread t = new Thread(new InfiniteTask());
        t.start();

        // Wait a while
        Thread.sleep(5000);

        System.out.println(t.isInterrupted()); // false
        t.interrupt();              // <== Sets the interrupt status flag in thread t
        System.out.println(t.isInterrupted()); // true

        System.out.println("-- main thread finished --");
    }

}


