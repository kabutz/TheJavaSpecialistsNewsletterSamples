package eu.javaspecialists.tjsn.issue193;

import java.util.*;

public class FullMapObjectFactory implements ObjectFactory {
    private final ObjectFactory factory;

    protected FullMapObjectFactory(ObjectFactory factory) {
        this.factory = factory;
    }

    public Object makeObject() {
        return fill((Map) factory.makeObject());
    }

    protected Map fill(Map map) {
        for (int i = -128; i < 128; i++) {
            map.put(i, "dummy");
        }
        return map;
    }
}