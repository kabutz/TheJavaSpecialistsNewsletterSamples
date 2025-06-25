package eu.javaspecialists.tjsn.issue108;

import java.util.*;

import static eu.javaspecialists.tjsn.issue108.BetterCollectionFactory.*;

public class BestCollectionTest {
    public static void main(String... args) {
        BetterCollection<String> names = asBetterCollection(
                new ArrayList<String>(), String.class);
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
