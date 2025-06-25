package eu.javaspecialists.tjsn.issue266;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class DebouncerHeinz<T> implements Debouncer<T> {
    private final Callback<T> callback;
    private final ScheduledExecutorService timer =
            Executors.newSingleThreadScheduledExecutor();
    private final Set<Holder<T>> jobs = new ConcurrentSkipListSet<>();

    public DebouncerHeinz(Callback<T> c, int time, TimeUnit unit) {
        this.callback = c;
        timer.scheduleAtFixedRate(this::processAll, time, time, unit);
    }

    private void processAll() {
        Set<Holder<T>> toNotify = new ConcurrentSkipListSet<>();
        for (Iterator<Holder<T>> it = jobs.iterator(); it.hasNext(); ) {
            Holder<T> holder = it.next();
            it.remove();
            toNotify.add(holder);
        }
        toNotify.forEach(h -> callback.call(h.t));
    }

    public void call(T t) {
        if (timer.isShutdown())
            throw new IllegalStateException("Debouncer shut down");
        Holder<T> holder = new Holder<>(t);
        jobs.add(holder);
    }

    public void shutdown() {
        timer.shutdown();
    }

    private static class Holder<T> implements Comparable<Holder<T>> {
        private final T t;
        private static final AtomicLong nextOrder = new AtomicLong();
        private final long order = nextOrder.incrementAndGet();

        public Holder(T t) {
            this.t = t;
        }

        public int compareTo(Holder<T> o) {
            if (t == o.t) return 0;

            int tid = System.identityHashCode(t);
            int oid = System.identityHashCode(o.t);
            // identity hashcodes can have duplicates
            if (tid == oid) return Long.compare(order, o.order);
            return Integer.compare(tid, oid);
        }
    }
}
