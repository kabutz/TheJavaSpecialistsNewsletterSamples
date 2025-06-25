package eu.javaspecialists.tjsn.issue266;

import java.util.*;

public class Threading01<E> {
    private final LinkedList<E> elements = new LinkedList<>();

    public boolean offer(E e) throws InterruptedException {
        elements.add(e);
        notify();
        return true;
    }

    public E take() throws InterruptedException {
        if (elements.isEmpty()) wait();
        return elements.removeFirst();
    }
}
