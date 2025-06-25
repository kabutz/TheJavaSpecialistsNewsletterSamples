package eu.javaspecialists.tjsn.issue061;

public class Singleton2 {
    private static Singleton2 instance;

    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }

    private Singleton2() {
    }
    // other methods that you would need to do the actual work
}
