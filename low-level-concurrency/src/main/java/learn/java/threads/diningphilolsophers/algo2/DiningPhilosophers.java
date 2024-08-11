package learn.java.threads.diningphilolsophers.algo2;


public class DiningPhilosophers {

    public static final int NUM_PHILOSOPHERS = 5;

    private enum State {
        THINKING, HUNGRY, EATING
    }

    private int[] eatCounters = new int[NUM_PHILOSOPHERS];
    private State[] state = new State[NUM_PHILOSOPHERS];
    private Object[] locks = new Object[NUM_PHILOSOPHERS];

    public DiningPhilosophers(){
        for (int i=0; i<NUM_PHILOSOPHERS; i++){
            state[i] = State.THINKING;
            locks[i] = new Object();
            eatCounters[i] = 0;
        }
    }

    public void pickUp(int i){
        synchronized (locks[i]) {
            state[i] = State.HUNGRY;
            tryToEat(i); // Try to eat
            while (state[i] != State.EATING) {
                try {
                    locks[i].wait();
                } catch (InterruptedException e) {
                    // Do nothing, recheck condition and resume waiting if not fulfilled
                }
            }
        }
    }

    public void putDown(int philosopoherId){
        synchronized (locks[philosopoherId]) {
            state[philosopoherId] = State.THINKING;
            tryToEat(leftOf(philosopoherId));      // Try to wake the left one
            tryToEat(rightOf(philosopoherId));    // Try to wake the right one
        }
    }

    public void tryToEat(int philosopherId){
        synchronized (locks[philosopherId]){
            if (state[leftOf(philosopherId)] != State.EATING && state[philosopherId] == State.HUNGRY && state[rightOf(philosopherId)] != State.EATING){
                // Philosopher i can eat because non of his neighbours are eating
                state[philosopherId] = State.EATING;
                System.out.println("Philosopher " + philosopherId + " is about to eat " + eatCounters[philosopherId] + " times.");
                eatCounters[philosopherId]++;
                locks[philosopherId].notifyAll();
            }
        }
    }

    /**
     * Returns the index of the philosopher who is left of philosopher i
     *
     * @param philosopherId
     * @return int
     */
    private int leftOf(int philosopherId){
        return (philosopherId + 1) % NUM_PHILOSOPHERS;
    }

    /**
     * Returns the index of the philosopher who is to the right of philosopher i
     *
     * @param philosopherId
     * @return int
     */
    private int rightOf(int philosopherId){
        return (philosopherId + NUM_PHILOSOPHERS - 1) % NUM_PHILOSOPHERS;
    }


    /********************************************************************************************************************
     * Runs the program
     * @param args
     ********************************************************************************************************************/
    public static void main(String[] args) {
        int numOfDiners = DiningPhilosophers.NUM_PHILOSOPHERS;
        DiningPhilosophers diningPhilosophers2 = new DiningPhilosophers();

        Thread[] diners = new Thread[numOfDiners];
        for (int i=0; i<numOfDiners; i++){

            final int index = i;
            Thread t = new Thread(() -> {
                while (true) {
                    diningPhilosophers2.pickUp(index);
                    doAction("EATING");
                    diningPhilosophers2.putDown(index);
                    doAction("THINKING");
                }
            });
            diners[i] = t;
            t.start();
        }
    }

    private static void doAction(String action) {
        System.out.println(Thread.currentThread().getName() + " " + action);
        try {
            Thread.sleep(((int) (Math.random() * 1000)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
