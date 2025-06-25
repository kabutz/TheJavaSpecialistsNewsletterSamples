package eu.javaspecialists.tjsn.issue112;

import java.util.*;

public class NullIterator implements Iterator {
    private static final Iterator iterator = new NullIterator();

    // since we cannot get any objects with next(),
    // we can safely cast it to your type, so we can
    // suppress the warnings
    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> getIterator() {
        return iterator;
    }

    private NullIterator() {
    }

    // always empty!
    public boolean hasNext() {
        return false;
    }

    // Empty collections would throw NoSuchElementException
    public Object next() {
        throw new NoSuchElementException("Null Iterator");
    }

    // Should only be called after a next() has been called.
    // Since next() can never be called, the correct exception
    // to throw is the IllegalStateException.
    public void remove() {
        throw new IllegalStateException("Null Iterator");
    }
}
