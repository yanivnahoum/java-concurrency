package learn.java.threads;

public class Utils {
    public static long now(){
        System.out.println("Start");
        return System.currentTimeMillis();
    }

    public static long elapsedFrom(long time){
        long elapsed = System.currentTimeMillis() - time;
        System.out.println("Took: " + elapsed);
        return elapsed;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
