package eu.javaspecialists.tjsn.issue105;

public class Singleton2 {
    private static Singleton2 instance;

    // lazy initialization
    public static Singleton2 getInstance() {
        synchronized (Singleton2.class) {
            if (instance == null) {
                instance = new Singleton2();
            }
        }
        return instance;
    }

    private Singleton2() {
    }
}
