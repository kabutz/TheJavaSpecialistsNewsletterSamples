package eu.javaspecialists.tjsn.issue284;

import java.util.*;
import java.util.function.*;

public interface PrimitiveIterable<T, T_CONS> extends Iterable<T> {
    void forEach(T_CONS action);

    interface OfInt extends PrimitiveIterable<Integer, IntConsumer> {
        PrimitiveIterator.OfInt iterator();

        default Spliterator.OfInt spliterator() {
            return Spliterators.spliteratorUnknownSize(iterator(), 0);
        }

        default void forEach(IntConsumer action) {
            Objects.requireNonNull(action);
            for (PrimitiveIterator.OfInt it = iterator(); it.hasNext(); )
                action.accept(it.nextInt());
        }
    }

    interface OfLong extends PrimitiveIterable<Long, LongConsumer> {
        PrimitiveIterator.OfLong iterator();

        default Spliterator.OfLong spliterator() {
            return Spliterators.spliteratorUnknownSize(iterator(), 0);
        }

        default void forEach(LongConsumer action) {
            Objects.requireNonNull(action);
            for (PrimitiveIterator.OfLong it = iterator(); it.hasNext(); )
                action.accept(it.nextLong());
        }
    }

    interface OfDouble extends PrimitiveIterable<Double, DoubleConsumer> {
        PrimitiveIterator.OfDouble iterator();

        default Spliterator.OfDouble spliterator() {
            return Spliterators.spliteratorUnknownSize(iterator(), 0);
        }

        default void forEach(DoubleConsumer action) {
            Objects.requireNonNull(action);
            for (PrimitiveIterator.OfDouble it = iterator(); it.hasNext(); )
                action.accept(it.nextDouble());
        }
    }
}
