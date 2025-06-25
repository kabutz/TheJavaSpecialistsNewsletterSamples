package eu.javaspecialists.tjsn.issue284;

import java.util.*;
import java.util.function.*;

public interface IntIterable {
    PrimitiveIterator.OfInt iterator();

    default void forEach(IntConsumer action) {
        Objects.requireNonNull(action);
        for (PrimitiveIterator.OfInt it = iterator(); it.hasNext(); )
            action.accept(it.next());
    }

    default Spliterator.OfInt spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
}

