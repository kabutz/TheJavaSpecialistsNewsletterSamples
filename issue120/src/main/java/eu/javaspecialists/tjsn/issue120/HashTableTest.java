package eu.javaspecialists.tjsn.issue120;

import java.util.*;

public class HashTableTest {
    public static void main(String[] args) {
        System.out.println("HashMap test");
        HashMap hm = new HashMap();
        hm.put(null, "hello");
        System.out.println("Hashtable test");
        Hashtable hs = new Hashtable();
        hs.put(null, "hello");
    }
}
