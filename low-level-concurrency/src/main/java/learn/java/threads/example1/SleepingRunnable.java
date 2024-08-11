package learn.java.threads.example1;

public class SleepingRunnable implements Runnable {

    private final long sleepTime;

    SleepingRunnable(long sleepTime){
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        try {
            System.out.println("-- thread: going to sleep --");
            Thread.sleep(sleepTime);  // Sleep times are not guaranteed to be precise
            System.out.println("-- thread: im awake! --");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


