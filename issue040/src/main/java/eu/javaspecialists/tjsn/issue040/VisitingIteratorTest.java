package eu.javaspecialists.tjsn.issue040;

import java.util.*;

public class VisitingIteratorTest {
    public static void main(String[] args) {
        Collection c = new LinkedList();
        for (int i = 0; i < 3; i++) c.add(new Integer(i));
        VisitingIterator vit = new VisitingIterator();

        vit.visit(c, new Object() {
            public void execute(Integer i) {
                System.out.println(i.intValue() + 10);
            }
        });

        System.out.println();

        c.add(new Float(2.1));
        c.add("Hello");

        vit.visit(c, new Object() {
            public void execute(Object o) {
                System.out.println(o);
            }

            public void execute(Number n) {
                System.out.println(n.intValue() + 20);
            }

            public void execute(Integer i) {
                System.out.println(i.intValue() + 10);
            }

            public void execute(String s) {
                System.out.println(s.toLowerCase());
            }
        });

        System.out.println();

        vit.visit(c, new Object() {
            public void execute(Integer i) {
                System.out.println(i.intValue() + 10);
            }
        });
    }
}
