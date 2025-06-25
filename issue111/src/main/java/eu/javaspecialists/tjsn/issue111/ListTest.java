package eu.javaspecialists.tjsn.issue111;

import java.util.*;

public class ListTest {
    private static final int NUM_ELEMENTS = 100 * 1000;

    public static void main(String[] args) {
        List ar = new ArrayList();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            ar.add(i);
        }
        testListBeginning(ar);
        testListBeginning(new LinkedList(ar));
        testListMiddle(ar);
        testListMiddle(new LinkedList(ar));
        testListEnd(ar);
        testListEnd(new LinkedList(ar));
    }

    public static void testListBeginning(List list) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list.add(0, new Object());
            list.remove(0);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("beginning " +
                list.getClass().getSimpleName() + " took " + time);
    }

    public static void testListMiddle(List list) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list.add(NUM_ELEMENTS / 2, new Object());
            list.remove(NUM_ELEMENTS / 2);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("middle    " +
                list.getClass().getSimpleName() + " took " + time);
    }

    public static void testListEnd(List list) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            list.add(new Object());
            list.remove(NUM_ELEMENTS);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("end       " +
                list.getClass().getSimpleName() + " took " + time);
    }
}
