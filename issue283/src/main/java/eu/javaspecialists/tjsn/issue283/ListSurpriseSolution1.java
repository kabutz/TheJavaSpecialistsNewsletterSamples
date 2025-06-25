package eu.javaspecialists.tjsn.issue283;

import java.util.*;

public class ListSurpriseSolution1 extends ListSurprise {
    private static void doSomething(List<Integer> list) {
        // Remove element at index 5 and modify list 4 billion times
        list.remove(5);
        for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
            ((ArrayList<Integer>) list).trimToSize();
        }
    }
}
