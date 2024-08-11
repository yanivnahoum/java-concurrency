package learn.java.threads.example5;

/**
 * https://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
 *
 * A write to volatile object is guaranteed to happen after all the writes to that object and therefore
 * a reordering will not occur
 *
 * Please note that in general, using a singleton pattern is not recommended.
 *
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
                    instance = new Singleton();    // This instruction might be reordered having the assignment BEFORE object init
                                                   // causing threads, in rare cases, to obtain an un-initialized object
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
