package learn.java.threads.example6;

import java.util.concurrent.atomic.AtomicReference;

public interface Plane {
    void updateLocation(int x, int y);
    Location getLocation();
}

class SyncPlane implements Plane {
    private Location location = new Location(0,0);

    @Override
    public synchronized void updateLocation(int x, int y) {
        this.location.setX(x);
        this.location.setY(y);
    }

    @Override
    public synchronized Location getLocation() {
        return this.location;
    }
}

class AtomicRefPlane implements Plane {
    private AtomicReference<Location> locationRef = new AtomicReference<>(new Location(0,0));

    @Override
    public void updateLocation(int x, int y) {
        Location newLocation = new Location(x, y);
        Location currentLocation = null;
        do {
            currentLocation = locationRef.get();
        } while (!locationRef.compareAndSet(currentLocation, newLocation));
    }

    @Override
    public Location getLocation() {
        return locationRef.get();
    }
}
