package eu.javaspecialists.tjsn.issue087;

import sun.reflect.*;

public class Potato {
    private final int id;
    private Soup soup;

    public Potato(int id) {
        this.id = id;
    }

    public void setSoup(Soup soup) {
        this.soup = soup;
        if (Reflection.getCallerClass(2) != Soup.class) {
            soup.add(this);
        }
    }

    public Soup getSoup() {
        return soup;
    }

    public String toString() {
        return "Potato " + id;
    }
}