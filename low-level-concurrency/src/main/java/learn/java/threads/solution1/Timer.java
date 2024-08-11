package learn.java.threads.solution1;

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

    public void start() {
        this.timerThread = new Thread(this);
        this.timerThread.start();
    }

    public void stop() {
        this.timerThread.interrupt();
    }

    public void reset() {
        this.timerAppGui.setNewTimerValue(this.resetValue);
        this.value = this.resetValue;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        this.isRunning = true;
        while (value > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                this.isRunning = false;
                return;
            }
            value--;
            timerAppGui.setNewTimerValue(value);
        }
        this.isRunning = false;
    }
}
