package eu.javaspecialists.tjsn.issue290;

import java.lang.reflect.*;
import java.util.*;

/**
 * This is an example of a bad bad ArrayList. Don't use!
 */
public final class BadArrayList<E> extends AbstractList<E> {
    private final Object[] elements;
    private final Class<E> type;

    public BadArrayList(Collection<? extends E> collection,
                        Class<E> type) {
        elements = collection.toArray(); // dangerous
        this.type = type;
    }

    public E get(int index) {
        return type.cast(elements[index]);
    }

    public E set(int index, E element) {
        E result = get(index);
        elements[index] = element;
        return result;
    }

    public int size() {
        return elements.length;
    }

    public E[] toArray() {
        E[] copy = (E[]) Array.newInstance(type, elements.length);
        System.arraycopy(elements, 0, copy, 0,
                Math.min(elements.length, elements.length));
        return copy;
    }
}
