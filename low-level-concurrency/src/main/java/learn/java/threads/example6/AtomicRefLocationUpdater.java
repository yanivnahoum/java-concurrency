package learn.java.threads.example6;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicRefLocationUpdater implements Runnable {

    private AtomicReference<Location> locationRef = new AtomicReference<>(new Location(0,0));

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < 1_000_000; i++) {
            int x = random.nextInt(1000);
            int y = random.nextInt(1000);
            Location newLocation = null;
            Location currentLocation = null;
            do {
                newLocation = new Location(x,y);
                currentLocation = locationRef.get();
            } while (!locationRef.compareAndSet(currentLocation, newLocation));
        }
        System.out.println("Final value: " + locationRef.get());
    }
}
