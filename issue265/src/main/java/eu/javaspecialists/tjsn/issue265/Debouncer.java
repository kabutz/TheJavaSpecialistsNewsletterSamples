package eu.javaspecialists.tjsn.issue265;

public interface Debouncer<T> extends Callback<T> {
    void shutdown();
}
