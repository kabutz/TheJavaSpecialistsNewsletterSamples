package eu.javaspecialists.tjsn.issue108;

import java.lang.reflect.*;
import java.util.*;

import static eu.javaspecialists.tjsn.issue108.DynamicObjectAdapterFactory.*;

public class BetterCollectionFactory {
    public static <T> BetterCollection<T> asBetterCollection(
            final Collection<T> adaptee, final Class<T> valueType) {
        return adapt(adaptee,
                BetterCollection.class,
                // this anonymous inner class contains the method that
                // we want to adapt
                new Object() {
                    public T[] toArray() {
                        return adaptee.toArray((T[]) Array.newInstance(
                                valueType, adaptee.size()));
                    }

                    // Whilst we are at it, we could also make it into a
                    // checked collection, see java.util.Collections for
                    // an example.
                    public boolean add(T o) {
                        if (!valueType.isInstance(o))
                            throw new ClassCastException("Attempt to insert " +
                                    o.getClass() +
                                    " value into collection with value type " + valueType);
                        return adaptee.add(o);
                    }
                    // addAll left as an exercise for the reader :-)
                });
    }
}
