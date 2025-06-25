package eu.javaspecialists.tjsn.issue105;

public class Singleton3 {
    private static Singleton3 instance;

    // double-checked locking - broken in Java - don't use it!
    public static Singleton3 getInstance() {
        if (instance == null) {
            synchronized (Singleton3.class) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }

    private Singleton3() {
    }
}
