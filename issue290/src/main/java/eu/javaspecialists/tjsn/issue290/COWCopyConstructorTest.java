package eu.javaspecialists.tjsn.issue290;

import java.util.*;
import java.util.concurrent.*;

public class COWCopyConstructorTest {
    private static class BadVector<E> extends Vector<E> {
        public synchronized Object[] toArray() {
            trimToSize();
            return elementData;
        }
    }

    public static void main(String... args) {
        Collection<String> list = new BadVector<String>();
        Collections.addAll(list, "John", "Anton", "Heinz");
        CopyOnWriteArrayList<String> cowList =
                new CopyOnWriteArrayList<String>(list);
        Object[] values = list.toArray();
        values[0] = "Diane";
        System.out.println("list = " + list);
        System.out.println("cowList = " + cowList);
    }
}
