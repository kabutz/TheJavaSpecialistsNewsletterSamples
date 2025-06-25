package eu.javaspecialists.tjsn.issue108;

import java.lang.reflect.*;
import java.util.*;

public class BetterArrayList<T> extends ArrayList<T> {
    private final Class<T> valueType;

    public BetterArrayList(int initialCapacity, Class<T> valueType) {
        super(initialCapacity);
        this.valueType = valueType;
    }

    public BetterArrayList(Class<T> valueType) {
        this.valueType = valueType;
    }

    public BetterArrayList(Collection<? extends T> ts,
                           Class<T> valueType) {
        super(ts);
        this.valueType = valueType;
    }

    // You can modify the return type of an overridden method in
    // Java 5, with some restrictions.
    public T[] toArray() {
        return toArray((T[]) Array.newInstance(valueType, size()));
    }
}
