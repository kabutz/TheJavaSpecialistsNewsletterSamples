package eu.javaspecialists.tjsn.issue087;

import java.util.*;

public class Soup {
    private final List potatos = new ArrayList();

    public void add(Potato p) {
        potatos.add(p);
        p.setSoup(this);
    }

    public String toString() {
        return "Soup {potatos=" + potatos + "}";
    }
}