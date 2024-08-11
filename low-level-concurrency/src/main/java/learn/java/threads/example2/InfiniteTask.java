package learn.java.threads.example2;

public class InfiniteTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Thread: starting infinite loop...");
        while (true){
            this.someLongLastingAction();
            if (Thread.interrupted()){
                System.out.println("Thread Interrupted: terminating infinite loop...");
                return;     // <== We might want to throw InterruptedException here
            }
        }
    }

    private void someLongLastingAction() {
        //Some Long Lasting Action
        System.out.println("Working Hard");
        for (long i = 0; i < 1L<<32; i++) {
        }
    }

}
