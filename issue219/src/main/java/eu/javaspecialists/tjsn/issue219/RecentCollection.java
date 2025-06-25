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

public class RecentCollection<E> implements Iterable<E> {
    private static final int MAXIMUM = 10;
    private final int maximumElements;

    private final Collection<E> recent =
            Collections.newSetFromMap(
                    new LinkedHashMap<E, Boolean>(32, 0.7f, true) {
                        protected boolean removeEldestEntry(
                                Map.Entry<E, Boolean> eldest) {
                            return size() > maximumElements;
                        }
                    }
            );

    public RecentCollection() {
        this(MAXIMUM);
    }

    public RecentCollection(int maximumElements) {
        this.maximumElements = maximumElements;
    }

    public void add(E element) {
        recent.add(element);
        // and maybe persist this in a property list ... ?
    }

    public void clear() {
        recent.clear();
        // and maybe clear the persisted property list ... ?
    }

    public Iterator<E> iterator() {
        // and maybe read the property list in case it changed ... ?
        return new ReverseCollection<E>(recent).iterator();
    }
}
