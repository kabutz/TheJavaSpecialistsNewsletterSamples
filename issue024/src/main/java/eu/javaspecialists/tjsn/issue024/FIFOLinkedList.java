package eu.javaspecialists.tjsn.issue024;

import java.util.*;

public class FIFOLinkedList extends LinkedList implements FIFO {
    public Object remove() {
        return remove(0);
    }
}