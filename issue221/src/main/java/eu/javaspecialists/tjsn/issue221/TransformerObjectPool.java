package eu.javaspecialists.tjsn.issue221;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

/**
 * In this approach, we only have as many objects as we've ever
 * had concurrent threads trying to do parsing/formatting.  This
 * way, we avoid creating ThreadLocals that might never get
 * cleaned up if the thread belongs to a thread pool.
 */
public class TransformerObjectPool<T> extends Transformer<T> {
    private final Queue<T> objects = new ConcurrentLinkedQueue<>();
    private final Supplier<T> supplier;

    public TransformerObjectPool(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    protected T takeTransformer() {
        T t = objects.poll();
        if (t == null) {
            t = supplier.get();
        }
        return t;
    }

    protected void putTransformer(T t) {
        objects.add(t);
    }
}