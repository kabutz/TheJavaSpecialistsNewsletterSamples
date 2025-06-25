package eu.javaspecialists.tjsn.issue193;


import java.util.concurrent.*;

public class ConcurrentHashMapFactory implements ObjectFactory {
    public Object makeObject() {
        return new ConcurrentHashMap();
    }
}
