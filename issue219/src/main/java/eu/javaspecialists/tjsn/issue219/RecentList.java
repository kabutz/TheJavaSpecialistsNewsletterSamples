package eu.javaspecialists.tjsn.issue219;

import java.util.*;

public class RecentList<E> implements Iterable<E> {
    private final ArrayList<E> recent = new ArrayList<>();
    private final int maxLength;

    public RecentList(int maxLength) {
        this.maxLength = maxLength;
    }

    public void add(E element) {
        recent.remove(element);
        recent.add(0, element);
        reduce();
    }

    private void reduce() {
        while (recent.size() > maxLength) {
            recent.remove(recent.size() - 1);
        }
    }

    public void clear() {
        recent.clear();
    }

    public Iterator<E> iterator() {
        return Collections.unmodifiableCollection(recent).iterator();
    }
}
