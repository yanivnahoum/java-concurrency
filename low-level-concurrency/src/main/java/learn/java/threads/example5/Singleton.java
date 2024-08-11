package learn.java.threads.example5;

/**
 * https://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
 */
public class Singleton {

    private static /*volatile*/ Singleton instance = null;

    private int x;
    private int y;

    private Singleton(){
        // initialization code
        this.x = 1;
        this.y = 2;
    }

    /**
     * Not Safe and incorrect implementation!!
     *
     * @return
     */
    public static Singleton instance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    // Allocate Object
                    // Initialize Object
                    // Assign Object to instance
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        Singleton s = Singleton.instance();
        s.toString();
    }

}
