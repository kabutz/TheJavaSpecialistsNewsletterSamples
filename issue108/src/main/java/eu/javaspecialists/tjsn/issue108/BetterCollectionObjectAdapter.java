package eu.javaspecialists.tjsn.issue108;

import java.lang.reflect.*;
import java.util.*;

public class BetterCollectionObjectAdapter<T>
        implements BetterCollection<T> {
    private final Collection<T> adaptee;
    private final Class<T> valueType;

    public BetterCollectionObjectAdapter(Collection<T> adaptee,
                                         Class<T> valueType) {
        this.adaptee = adaptee;
        this.valueType = valueType;
    }

    public T[] toArray() {
        return adaptee.toArray((T[]) Array.newInstance(valueType,
                adaptee.size()));
    }

    // this is a typical problem with the Object Adapter Design
    // Pattern - you have implement all the methods :-(
    public int size() {
        return adaptee.size();
    }

    public boolean isEmpty() {
        return adaptee.isEmpty();
    }

    public boolean contains(Object o) {
        return adaptee.contains(o);
    }

    public Iterator<T> iterator() {
        return adaptee.iterator();
    }

    public <T> T[] toArray(T[] ts) {
        return adaptee.toArray(ts);
    }

    public boolean add(T t) {
        return adaptee.add(t);
    }

    public boolean remove(Object o) {
        return adaptee.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return adaptee.containsAll(c);
    }

    public boolean addAll(Collection<? extends T> ts) {
        return adaptee.addAll(ts);
    }

    public boolean removeAll(Collection<?> c) {
        return adaptee.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return adaptee.retainAll(c);
    }

    public void clear() {
        adaptee.clear();
    }
}
