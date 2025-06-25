package eu.javaspecialists.tjsn.issue090;

import java.util.*;

public class NewFor {
    public static void main(String... args) {
        // we can use type-safe collections...
        Collection<String> names = new ArrayList<String>();
        names.add("Maxi");
        names.add("Connie");
        names.add("Helene");
        names.add("Heinz");
        //names.add(new Integer(42)); -- does not compile!
        // look at the new for construct:
        for (String name : names) {
            System.out.println("name = " + name);
        }
    }
}
