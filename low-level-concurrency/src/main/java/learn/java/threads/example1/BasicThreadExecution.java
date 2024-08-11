package learn.java.threads.example1;

/**
 * How to create a simple thread with a runnable task
 */
public class BasicThreadExecution {

    public static void main(String[] args) {
        System.out.println("-- main thread starts --");

        // Create a thread and link it with a Runnable object
        Thread t = new Thread(new SleepingRunnable(5000));

        // Create another sleeper using lambda
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("-- t1: going to sleep --");
                Thread.sleep(5000);
                System.out.println("-- t1: im awake! --");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Create another sleeper using method ref
        Thread t2 = new Thread(BasicThreadExecution::runMe);

        // Start the threads
        t.start();
        t1.start();
        t2.start();

        System.out.println("-- main thread finished --");
    }

    private static void runMe() {
        try {
            System.out.println("-- t2: going to sleep --");
            Thread.sleep(5000);
            System.out.println("-- t2: im awake! --");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



