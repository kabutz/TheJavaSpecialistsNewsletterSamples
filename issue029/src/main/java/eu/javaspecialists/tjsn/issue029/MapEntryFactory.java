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

package eu.javaspecialists.tjsn.issue029;

import java.util.*;

public class MapEntryFactory implements ObjectFactory {
    public Object makeObject() {
        return new Entry[]{
                new Entry(3234, Integer.toString(20), new Byte((byte) 20), null),
                new Entry(3234, new Object(), new Object(), null),
                new Entry(3234, new Object(), new Object(), null),
                new Entry(3234, new Object(), new Object(), null),
                new Entry(3234, new Object(), new Object(), null),
                new Entry(3234, new Object(), new Object(), null)};
    }

    static class Entry implements Map.Entry {
        final Object key;
        Object value;
        final int hash;
        Entry next;

        Entry(int h, Object k, Object v, Entry n) {
            value = v;
            next = n;
            key = k;
            hash = h;
        }

        public Object getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public Object setValue(Object value) {
            return value;
        }
    }
}
