package eu.javaspecialists.tjsn.issue193;


import java.util.*;

public class SynchronizedHashMapFactory implements ObjectFactory {
    public Object makeObject() {
        return Collections.synchronizedMap(new HashMap());
    }
}
