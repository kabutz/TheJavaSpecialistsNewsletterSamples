package eu.javaspecialists.tjsn.issue188;

import org.jpatterns.gof.*;

@StrategyPattern.Strategy
public interface InterlockTask<T> {
    boolean isDone();

    /**
     * The call() method is called interleaved by the the threads
     * in a round-robin fashion.
     */
    void call();

    /**
     * Returns the result after all the call()'s have completed.
     */
    T get();

    void reset();
}
