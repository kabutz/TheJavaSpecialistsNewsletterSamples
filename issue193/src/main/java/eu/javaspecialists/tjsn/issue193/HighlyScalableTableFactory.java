package eu.javaspecialists.tjsn.issue193;

import org.jctools.maps.*;

// moved to JCTools repository
public class HighlyScalableTableFactory implements ObjectFactory {
    public Object makeObject() {
        return new NonBlockingHashMap();
    }
}
