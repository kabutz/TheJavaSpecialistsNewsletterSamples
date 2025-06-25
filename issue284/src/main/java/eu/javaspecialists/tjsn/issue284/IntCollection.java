package eu.javaspecialists.tjsn.issue284;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public interface IntCollection extends PrimitiveIterable.OfInt {
    int size();
    boolean isEmpty();
    boolean contains(int element);
    int[] toArray();
    boolean add(int element);
    boolean remove(int element);
    boolean containsAll(IntCollection c);
    boolean addAll(IntCollection c);
    boolean removeAll(IntCollection c);
    boolean retainAll(IntCollection c);
    void clear();

    default boolean removeIf(IntPredicate filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final PrimitiveIterator.OfInt each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.nextInt())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    default IntStream stream() {
        return StreamSupport.intStream(spliterator(), false);
    }

    default IntStream parallelStream() {
        return StreamSupport.intStream(spliterator(), true);
    }
}
