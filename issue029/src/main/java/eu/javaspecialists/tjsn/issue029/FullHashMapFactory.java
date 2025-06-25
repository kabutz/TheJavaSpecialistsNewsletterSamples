package eu.javaspecialists.tjsn.issue029;

import java.util.*;

public class FullHashMapFactory implements ObjectFactory {
    public Object makeObject() {
        HashMap hm = new HashMap();
        for (int i = -128; i < 128; i++) {
            hm.put("" + i, new Byte((byte) i));
        }
        return hm;
    }
}
