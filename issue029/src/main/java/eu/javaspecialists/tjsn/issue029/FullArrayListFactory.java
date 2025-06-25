package eu.javaspecialists.tjsn.issue029;

import java.util.*;

public class FullArrayListFactory implements ObjectFactory {
    public Object makeObject() {
        ArrayList result = new ArrayList(10000);
        for (int i = 0; i < 10000; i++) {
            result.add(new Object());
        }
        return result;
    }
}