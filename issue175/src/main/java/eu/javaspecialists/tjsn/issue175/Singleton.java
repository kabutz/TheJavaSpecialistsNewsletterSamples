package eu.javaspecialists.tjsn.issue175;

public class Singleton {
    private static final Singleton instance = new Singleton();

    private Singleton() {
        if (instance != null) {
            throw new IllegalStateException("already initialized");
        }
    }

    public static Singleton getInstance() {
        return instance;
    }

    // etc.
}
