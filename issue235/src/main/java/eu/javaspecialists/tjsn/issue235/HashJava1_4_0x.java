package eu.javaspecialists.tjsn.issue235;

public class HashJava1_4_0x {
    int hash(Object key) {
        int h = key == null ? 0 : key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
}
