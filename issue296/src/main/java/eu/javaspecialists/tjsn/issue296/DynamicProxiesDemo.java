package eu.javaspecialists.tjsn.issue296;

// Maven: eu.javaspecialists.books.dynamicproxies:core:2.0.0

import eu.javaspecialists.books.dynamicproxies.*;

import java.util.*;

public class DynamicProxiesDemo {
    public static void main(String... args) {
        Set<String> angrySet = Proxies.castProxy(
                Set.class,
                (p, m, a) -> {
                    throw new UnsupportedOperationException(
                            m.getName() + "() not implemented");
                }
        );

        Set<String> set = Proxies.adapt(
                Set.class, // target interface
                angrySet, // adaptee
                new ConcurrentLinkedReducedHashSet<>() // adapter
        );
        set.add("hello");
        set.add("world");
        Iterator<String> it = set.iterator();
        set.add("Goodbye");
        while (it.hasNext()) {
            String next = it.next();
            System.out.println(next);
        }
        set.clear();
        set.addAll(List.of("one")); // UnsupportedOperationException
    }
}
