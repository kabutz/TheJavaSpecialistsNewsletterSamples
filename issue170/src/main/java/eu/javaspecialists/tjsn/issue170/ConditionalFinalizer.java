package eu.javaspecialists.tjsn.issue170;

public class ConditionalFinalizer {
    private static final boolean DEBUG = true;

    // Should be volatile as it is accessed from multiple threads.
    // Thanks to Anton Muhin for pointing that out.
    private volatile boolean resourceClosed;
    private final int id;

    public ConditionalFinalizer(int id) {
        this.id = id;
        resourceClosed = false;
    }

    protected void finalize() throws Throwable {
        if (DEBUG) {
            if (!resourceClosed) {
                System.err.println(
                        "You forgot to close the resource with id " + id);
                close();
            }
            super.finalize();
        }
    }

    public void close() {
        resourceClosed = true;
    }
}
