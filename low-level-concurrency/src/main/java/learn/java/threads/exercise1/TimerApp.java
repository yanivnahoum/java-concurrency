package learn.java.threads.exercise1;

public class TimerApp {
    private static final int TIMER_VALUE = 10;

    public static void main(String[] args) {
        TimerAppGui gui = new TimerAppGui(TIMER_VALUE);
        gui.start();
    }
}
