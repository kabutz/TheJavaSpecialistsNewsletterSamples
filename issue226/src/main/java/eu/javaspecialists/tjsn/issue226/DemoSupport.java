package eu.javaspecialists.tjsn.issue226;

import java.util.function.*;

public interface DemoSupport {
    static Predicate<Thread> createPredicate() {
        return (Thread t) ->
                t.getName().matches("pool-\\d+-thread-\\d+");
    }

    static Consumer<Thread> createConsumer() {
        return (Thread creator) -> {
            throw new SecurityException(creator +
                    " tried to create a thread");
        };
    }

    static Runnable createHelloJob() {
        return () -> System.out.printf(
                "Hello from \"%s\"",
                Thread.currentThread()
        );
    }
}
