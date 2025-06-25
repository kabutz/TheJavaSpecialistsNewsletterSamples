package eu.javaspecialists.tjsn.issue176;

import java.util.*;

public class ImmutableArrayList<E> implements Iterable<E> {
    private final Object[] elements;

    public ImmutableArrayList() {
        this.elements = new Object[0];
    }

    private ImmutableArrayList(Object[] elements) {
        this.elements = elements;
    }

    public int size() {
        return elements.length;
    }

    public ImmutableArrayList<E> add(E e) {
        Object[] new_elements = new Object[elements.length + 1];
        System.arraycopy(elements, 0,
                new_elements, 0, elements.length);
        new_elements[new_elements.length - 1] = e;
        return new ImmutableArrayList<E>(new_elements);
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int pos = 0;

            public boolean hasNext() {
                return pos < elements.length;
            }

            public E next() {
                return (E) elements[pos++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
