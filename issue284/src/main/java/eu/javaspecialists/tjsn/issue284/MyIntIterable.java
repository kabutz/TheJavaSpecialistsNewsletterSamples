package eu.javaspecialists.tjsn.issue284;

import java.util.*;
import java.util.function.*;

public interface MyIntIterable {
    MyIntIterator iterator();

    default void forEach(IntConsumer action) {
        Objects.requireNonNull(action);
        for (MyIntIterator it = iterator(); it.hasNext(); )
            action.accept(it.next());
    }

    default Spliterator.OfInt spliterator() {
        return new MyIntIteratorSpliterator(iterator(), 0);
    }
}
