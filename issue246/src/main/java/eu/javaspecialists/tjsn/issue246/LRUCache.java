package eu.javaspecialists.tjsn.issue246;

import java.util.*;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int maxEntries;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public LRUCache(int initialCapacity,
                    float loadFactor,
                    int maxEntries) {
        super(initialCapacity, loadFactor, true);
        this.maxEntries = maxEntries;
    }

    public LRUCache(int initialCapacity,
                    int maxEntries) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, maxEntries);
    }

    public LRUCache(int maxEntries) {
        this(DEFAULT_INITIAL_CAPACITY, maxEntries);
    }

    // not very useful constructor
    public LRUCache(Map<? extends K, ? extends V> m,
                    int maxEntries) {
        this(m.size(), maxEntries);
        putAll(m);
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxEntries;
    }
}
