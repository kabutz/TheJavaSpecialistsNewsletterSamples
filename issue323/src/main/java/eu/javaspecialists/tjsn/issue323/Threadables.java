package eu.javaspecialists.tjsn.issue323;

import jdk.internal.vm.*;

import java.util.function.*;
import java.util.stream.*;

// Facade for Threadables
public class Threadables {
    private Threadables() {}

    private static final ThreadContainer ROOT_CONTAINER =
            ThreadContainers.root();

    /**
     * Return a Threadable of the entire threads tree.
     */
public static Threadable create() {
        return new ThreadableComposite(ROOT_CONTAINER);
    }

    /**
     * Iterate througn the threads tree and apply the action to each thread.
     */
public static void forEach(Consumer<Thread> consumer) {
        create().accept(new ThreadableVisitor() {
            public void visit(Thread thread) {
                consumer.accept(thread);
            }
        });
    }

    /**
     * Create a flat stream of the raw Threads, not preserving the composite.
     */
public static Stream<Thread> stream() {
        return stream(ROOT_CONTAINER);
    }

    // Input by Cay Horstmann on how to create a concatenation of these streams
private static Stream<Thread> stream(ThreadContainer container) {
        return Stream.concat(container.threads(),
                container.children().flatMap(Threadables::stream));
    }
}
