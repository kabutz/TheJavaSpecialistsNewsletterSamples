package eu.javaspecialists.tjsn.issue040;

import java.util.*;

public class OldVisitingIteratorTest {
    public static void main(String[] args) {
        Collection c = new LinkedList();
        for (int i = 0; i < 3; i++) c.add(new Integer(i));

        Iterator it = c.iterator();
        while (it.hasNext()) {
            // lots of brackets - looks almost like Lisp - argh
            System.out.println(((Integer) it.next()).intValue() + 10);
        }

        c.add(new Float(2.1));
        c.add("Hello");

        it = c.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Integer) {
                System.out.println(((Integer) o).intValue() + 10);
            } else if (o instanceof Number) {
                System.out.println(((Number) o).intValue() + 20);
            } else if (o instanceof String) {
                System.out.println(((String) o).toLowerCase());
            } else {
                System.out.println(o);
            }
        }

        it = c.iterator();
        while (it.hasNext()) {
            System.out.println(((Integer) it.next()).intValue() + 10);
        }
    }
}
