package learn.java.threads.exercise1;

public class Timer implements Runnable {

    private final TimerAppGui timerAppGui;
    private final int resetValue;

    private Thread timerThread;
    private int value;
    private boolean isRunning;

    public Timer(TimerAppGui timerAppGui, int value) {
        this.timerAppGui = timerAppGui;
        this.value = this.resetValue = value;
        this.isRunning = false;
    }


    /**
     * Start the timer
     */
    public void start() {
        System.out.println("Starting/Stopping the timer...");
        /*
         * Start the timer thread by creating a new thread
         * and attach the runnable timer to it
         */
    }

    /**
     * Stop the timer
     */
    public void stop() {
        System.out.println("Stopping the timer...");
        /*
         * Make the currently running thread to terminate
         */
    }

    public void reset() {
        System.out.println("Resetting the timer counter...");
        /*
         * Reset the internal counter to the original value (resetValue) + update gui with the reset value
         */
    }

    public boolean isRunning() {
        return isRunning;  // Make sure
    }

    @Override
    public void run() {
        System.out.println("Timer thread started");
        /*
         * The timer should count backwards and decrease the timer value (and gui) every second
         * If timer was notified to stop then terminate its loop (consider using interrupt).
         * If timer reaches zero it should terminate.
         */
        System.out.println("Timer thread terminated");
    }
}
