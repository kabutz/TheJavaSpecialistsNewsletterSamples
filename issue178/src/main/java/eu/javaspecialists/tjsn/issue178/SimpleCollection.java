package eu.javaspecialists.tjsn.issue178;

import java.util.*;

public class SimpleCollection<E> extends AbstractCollection<E> {
    private final E[] values;

    public SimpleCollection(E[] values) {
        this.values = values.clone();
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int pos = 0;

            public boolean hasNext() {
                return pos < values.length;
            }

            public E next() {
                // Thanks to Volker Glave for pointing out that we need
                // to throw a NoSuchElementException if we try to go past
                // the end of the array.
                if (!hasNext()) throw new NoSuchElementException();
                return values[pos++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public int size() {
        return values.length;
    }
}
