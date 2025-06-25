package eu.javaspecialists.tjsn.issue178;

import java.util.*;
import java.util.concurrent.locks.*;

public class WalkingCollection<E>
        extends AbstractCollection<E> {
    private static final ThreadLocal<Boolean> iterating =
            new ThreadLocal<Boolean>() {
                protected Boolean initialValue() {
                    return false;
                }
            };
    private final Collection<E> wrappedCollection;
    private final ReentrantReadWriteLock rwlock =
            new ReentrantReadWriteLock();

    public WalkingCollection(Collection<E> wrappedCollection) {
        this.wrappedCollection = wrappedCollection;
    }

    public void iterate(Processor<E> processor) {
        rwlock.readLock().lock();
        try {
            iterating.set(true);
            for (E e : wrappedCollection) {
                if (!processor.process(e)) break;
            }
        } finally {
            iterating.set(false);
            rwlock.readLock().unlock();
        }
    }

    public Iterator<E> iterator() {
        rwlock.readLock().lock();
        try {
            final Iterator<E> wrappedIterator =
                    wrappedCollection.iterator();
            return new Iterator<E>() {
                public boolean hasNext() {
                    rwlock.readLock().lock();
                    try {
                        return wrappedIterator.hasNext();
                    } finally {
                        rwlock.readLock().unlock();
                    }
                }

                public E next() {
                    rwlock.readLock().lock();
                    try {
                        return wrappedIterator.next();
                    } finally {
                        rwlock.readLock().unlock();
                    }
                }

                public void remove() {
                    checkForIteration();
                    rwlock.writeLock().lock();
                    try {
                        wrappedIterator.remove();
                    } finally {
                        rwlock.writeLock().unlock();
                    }
                }
            };
        } finally {
            rwlock.readLock().unlock();
        }
    }

    public int size() {
        rwlock.readLock().lock();
        try {
            return wrappedCollection.size();
        } finally {
            rwlock.readLock().unlock();
        }
    }

    public boolean add(E e) {
        checkForIteration();
        rwlock.writeLock().lock();
        try {
            return wrappedCollection.add(e);
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    private void checkForIteration() {
        if (iterating.get())
            throw new IllegalMonitorStateException(
                    "Cannot modify whilst iterating");
    }
}
