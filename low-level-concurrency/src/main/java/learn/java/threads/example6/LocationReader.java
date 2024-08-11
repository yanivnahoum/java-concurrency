package learn.java.threads.example6;

public class LocationReader implements Runnable {
    public static int READ_COUNT = 100_000_000;

    private final Plane plane;

    public LocationReader(Plane plane){
        this.plane = plane;
    }

    @Override
    public void run() {
        Location location = null;
        for (int i = 0; i < READ_COUNT; i++) {
            location = this.plane.getLocation();
        }
        System.out.println("Reader last location: " + location);
    }
}
