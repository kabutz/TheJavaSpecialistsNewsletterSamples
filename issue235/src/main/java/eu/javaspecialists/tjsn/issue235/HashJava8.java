package eu.javaspecialists.tjsn.issue235;

public class HashJava8 {
    int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
