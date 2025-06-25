package eu.javaspecialists.tjsn.issue219;

import java.util.*;

/**
 * Provides an unmodifiable collection that returns the elements
 * in the reverse order of iteration.  If the original collection
 * implements the RandomAccess interface, we point to that,
 * otherwise we wrap it with an ArrayList.
 */
public class ReverseCollection<E> extends AbstractCollection<E> {
    private final List<E> elements;

    public ReverseCollection(Collection<E> original) {
        if (original instanceof RandomAccess) {
            elements = (List<E>) original;
        } else {
            elements = new ArrayList<E>(original);
        }
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = elements.size();

            public boolean hasNext() {
                return index > 0;
            }

            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (E) elements.get(--index);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public int size() {
        return elements.size();
    }
}
