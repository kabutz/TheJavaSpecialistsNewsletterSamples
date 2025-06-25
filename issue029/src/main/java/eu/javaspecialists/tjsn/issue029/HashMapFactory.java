package eu.javaspecialists.tjsn.issue029;

import java.util.*;

public class HashMapFactory implements ObjectFactory {
    public Object makeObject() {
        return new HashMap();
    }
}
