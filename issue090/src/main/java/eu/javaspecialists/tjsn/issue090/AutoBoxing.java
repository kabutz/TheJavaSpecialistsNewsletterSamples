package eu.javaspecialists.tjsn.issue090;

import java.util.*;

public class AutoBoxing {
    public static void main(String... args) {
        Collection<Integer> values = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        for (int val : values) {
            System.out.println(val);
        }
    }
}
