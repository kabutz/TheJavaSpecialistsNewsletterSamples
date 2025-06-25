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
