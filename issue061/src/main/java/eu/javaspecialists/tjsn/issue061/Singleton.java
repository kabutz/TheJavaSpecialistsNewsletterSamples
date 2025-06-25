package eu.javaspecialists.tjsn.issue061;

public class Singleton {
    private static final Singleton instance = new Singleton();

    public static Singleton getInstance() {
        return instance;
    }

    private Singleton() {
    }
    // other methods that you would need to do the actual work
}
