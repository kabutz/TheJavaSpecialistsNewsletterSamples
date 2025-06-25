package eu.javaspecialists.tjsn.issue144;

public class ThrowerConcurrent {
    private static ThreadLocal<Throwable> throwables =
            new ThreadLocal<Throwable>();

    private ThrowerConcurrent() throws Throwable {
        Throwable throwable = throwables.get();
        throwables.remove(); // avoid memory leak
        throw throwable;
    }

    public static void sneakyThrow(Throwable t) {
        throwables.set(t);
        try {
            ThrowerConcurrent.class.newInstance();
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
