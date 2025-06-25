package eu.javaspecialists.tjsn.issue024;

import java.util.*;

public class FIFOArrayList extends ArrayList implements FIFO {
    public Object remove() {
        return remove(0);
    }
}