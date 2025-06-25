package eu.javaspecialists.tjsn.issue193;


import java.util.concurrent.*;

public class SmallConcurrentHashMapFactory implements ObjectFactory {
    public Object makeObject() {
        return new ConcurrentHashMap(16, .75f, 2);
    }
}
