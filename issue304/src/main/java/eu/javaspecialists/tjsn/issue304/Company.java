package eu.javaspecialists.tjsn.issue304;

import java.io.*;
import java.util.*;

public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private final List<String> employees = new LinkedList<>();

    public void addEmployee(String name) {
        employees.add(name);
    }

    /**
     * Convenience method for you-know-who.
     */
    public void remove75PercentOfEmployees() {
        int pos = 0;
        for (var it = employees.iterator(); it.hasNext(); ) {
            it.next();
            if (pos++ % 4 != 0) it.remove();
        }
    }

    @Override
    public String toString() {
        return "Company{employees=" + employees + '}';
    }
}
