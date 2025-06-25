package eu.javaspecialists.tjsn.issue283;

import java.util.*;

public class ListSurprise {
    public static void main(String... args) {
        // Make ListSurprise print 3.14159
        System.setSecurityManager(new SecurityManager());
        List<Integer> numbers = new ArrayList<>();
        Collections.addAll(numbers, 3, 1, 4, 1, 5, 5, 9);
        Iterator<Integer> it = numbers.iterator();
        System.out.print(it.next()); // 3
        System.out.print('.');
        System.out.print(it.next()); // 1
        System.out.print(it.next()); // 4
        System.out.print(it.next()); // 1
        System.out.print(it.next()); // 5
        doSomething(numbers); // should make the next output be 9
        System.out.println(it.next());
        if (!numbers.equals(List.of(3, 1, 4, 1, 5, 9)))
            throw new AssertionError();
    }

    private static void doSomething(List<Integer> list) {
        // how???
    }
}
