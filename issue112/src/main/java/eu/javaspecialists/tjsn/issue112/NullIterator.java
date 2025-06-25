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
