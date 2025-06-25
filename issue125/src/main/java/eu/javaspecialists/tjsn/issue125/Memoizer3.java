package eu.javaspecialists.tjsn.issue125;

import java.util.*;
import java.util.concurrent.*;

public class Memoizer3<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache
            = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> eval = new Callable<V>() {
                public V call() throws InterruptedException {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(eval);
            f = ft;
            cache.put(arg, ft);
            ft.run(); // call to c.compute happens here
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            // Kabutz: this is my addition to the code...
            try {
                throw e.getCause();
            } catch (RuntimeException ex) {
                throw ex;
            } catch (Error ex) {
                throw ex;
            } catch (Throwable t) {
                throw new IllegalStateException("Not unchecked", t);
            }
        }
    }
}
