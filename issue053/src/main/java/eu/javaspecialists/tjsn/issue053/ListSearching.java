package eu.javaspecialists.tjsn.issue053;

/**
 * @author Mr M.O.Nument
 */

import java.util.*;

public class ListSearching {
    private List names = new LinkedList();

    public void f() {
        for (int i = 0; i < size(); i++) {
            if (names.get(i) == "Heinz")
                System.out.println("Found it");
        }
    }

    private int size() {
        int result = 0;
        Iterator it = names.iterator();
        while (it.hasNext()) {
            result++;
            it.next();
        }
        return result;
    }
}
