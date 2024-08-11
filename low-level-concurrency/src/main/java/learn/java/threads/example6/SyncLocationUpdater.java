package learn.java.threads.example6;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class SyncLocationUpdater implements Runnable {

    private Location location = new Location(0,0);

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < 100_000_000; i++) {
            int x = random.nextInt(1000);
            int y = random.nextInt(1000);

            this.setLocation(x,y);

        }
        System.out.println("Final value: " + location);
    }

    private synchronized void setLocation(int x, int y) {
        this.location.setX(x);
        this.location.setY(y);
    }
}
