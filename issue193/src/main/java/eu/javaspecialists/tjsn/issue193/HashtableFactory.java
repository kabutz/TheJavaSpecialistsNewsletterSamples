package eu.javaspecialists.tjsn.issue193;

import java.util.*;

public class HashtableFactory implements ObjectFactory {
    public Object makeObject() {
        return new Hashtable();
    }
}
