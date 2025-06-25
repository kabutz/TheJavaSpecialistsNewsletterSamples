/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    public void iterate(Processor<? super E> processor) {
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
