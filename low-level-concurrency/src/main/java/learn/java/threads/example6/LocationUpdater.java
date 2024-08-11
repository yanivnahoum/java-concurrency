package learn.java.threads.example6;

import java.util.Random;

public class LocationUpdater implements Runnable {
    public static int UPDATE_COUNT = 100_000_000;

    private final Plane plane;

    public LocationUpdater(Plane plane){
        this.plane = plane;
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < UPDATE_COUNT; i++) {
            int x = random.nextInt(1000);
            int y = random.nextInt(1000);

            this.plane.updateLocation(x, y);
        }
        System.out.println("Updater last location: " + this.plane.getLocation());
    }
}
