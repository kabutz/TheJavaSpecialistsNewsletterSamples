package eu.javaspecialists.tjsn.issue029;

import java.util.*;

public class SmallHashMapFactory implements ObjectFactory {
    public Object makeObject() {
        HashMap hm0 = new HashMap();
        hm0.put(new String("20"), new Byte((byte) 20));
        return hm0;
    }
}
