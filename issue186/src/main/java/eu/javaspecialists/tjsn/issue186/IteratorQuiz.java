package eu.javaspecialists.tjsn.issue186;

import java.util.*;

public class IteratorQuiz {
    public static void main(String... args) {
        final List<String> list = new ArrayList() {{add("Hello");}};
        final Iterator<String> iterator = list.iterator();
        System.out.println(iterator.next());
        list.add("World");
        // FIXME : work here while I'm sunbathing
        System.out.println(iterator.next());
    }
}