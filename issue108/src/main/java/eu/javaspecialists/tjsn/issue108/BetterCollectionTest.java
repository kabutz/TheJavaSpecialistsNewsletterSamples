package eu.javaspecialists.tjsn.issue108;

import java.util.*;

public class BetterCollectionTest {
    public static void main(String... args) {
        BetterCollection<String> names =
                new BetterCollectionObjectAdapter<String>(
                        new LinkedList<String>(), String.class);
        names.add("Wolfgang");
        names.add("Leander");
        names.add("Klaus");
        names.add("Reinhard");
        String[] nameArray = names.toArray();
        for (String s : nameArray) {
            System.out.println(s);
        }
    }
}
