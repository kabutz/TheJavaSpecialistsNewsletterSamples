package eu.javaspecialists.tjsn.issue053;

import java.util.*;

public class HashtableTest {
    public static void main(String[] args) {
        Hashtable ht = new Hashtable();
        ht.put("Heinz", "Kabutz");
        ht.put(ht, "all");
        ht.put("all", ht);
        ht.put(ht, ht);
        try {
            System.out.println(ht);
        } catch (StackOverflowError e) {
            System.out.println("Caused Stack Overflow Error!");
        }
    }
}
