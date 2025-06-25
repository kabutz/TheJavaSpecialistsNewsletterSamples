package eu.javaspecialists.tjsn.issue235;

public class HashJava1_4_0 {
    int hash(Object key) {
        int h = key == null ? 0 : key.hashCode();
        h += ~(h << 9);
        h ^= (h >>> 14);
        h += (h << 4);
        h ^= (h >>> 10);
        return h;
    }
}

