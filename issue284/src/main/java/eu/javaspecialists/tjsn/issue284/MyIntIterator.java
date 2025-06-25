package eu.javaspecialists.tjsn.issue284;

import java.util.*;
import java.util.function.*;

public interface MyIntIterator {
    boolean hasNext();

    int next();

    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    default void forEachRemaining(IntConsumer action) {
        Objects.requireNonNull(action);
        while (hasNext()) action.accept(next());
    }
}
