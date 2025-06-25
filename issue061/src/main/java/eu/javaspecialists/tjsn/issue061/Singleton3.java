package eu.javaspecialists.tjsn.issue061;

public class Singleton3 {
    private static Singleton3 instance;

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
    // other methods that you would need to do the actual work
}
