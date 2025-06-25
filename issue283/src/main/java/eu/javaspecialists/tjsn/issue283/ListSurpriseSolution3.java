package eu.javaspecialists.tjsn.issue283;

import java.util.*;

public class ListSurpriseSolution3 extends ListSurprise {
    private static void doSomething(List<Integer> list) {
        // Replace 5 with object that removes itself and returns "9"
        ((List) list).set(5, new Object() {
            public String toString() {
                list.remove(this);
                return "9";
            }
        });
    }
}
