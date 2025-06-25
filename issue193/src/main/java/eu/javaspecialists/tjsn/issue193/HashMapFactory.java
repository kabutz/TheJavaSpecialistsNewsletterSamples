package eu.javaspecialists.tjsn.issue193;

import java.util.*;

public class HashMapFactory implements ObjectFactory {
    public Object makeObject() {
        return new HashMap();
    }
}

