package eu.javaspecialists.tjsn.issue266;

public interface Debouncer<T> extends Callback<T> {
    void shutdown();
}
