package eu.javaspecialists.tjsn.issue226;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
import java.util.stream.*;

public class ThreadAccumulator implements Consumer<Thread> {
    private final ConcurrentMap<Thread, LongAdder> miscreants =
            new ConcurrentHashMap<>();

    public void accept(Thread thread) {
        miscreants.computeIfAbsent(
                thread, t -> new LongAdder()).increment();
    }

    public int getNumberOfMisbehavingThreads() {
        return miscreants.size();
    }

    public void forEach(BiConsumer<Thread, Integer> action) {
        miscreants.entrySet()
                .forEach(e -> action.accept(
                        e.getKey(),
                        e.getValue().intValue()));
    }

    public String toString() {
        return miscreants.entrySet()
                .parallelStream()
                .map(ThreadAccumulator::format)
                .collect(Collectors.joining(", "));
    }

    private static String format(Map.Entry<Thread, LongAdder> e) {
        return String.format("%s created %d thread(s)",
                e.getKey().getName(),
                e.getValue().intValue());
    }
}
