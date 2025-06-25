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
