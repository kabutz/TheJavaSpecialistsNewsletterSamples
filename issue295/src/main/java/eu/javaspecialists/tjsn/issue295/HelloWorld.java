package eu.javaspecialists.tjsn.issue295;

import java.util.*;

public class HelloWorld {
    public static void main(String... args) {
        var list = new ArrayList<String>();
        var stream = list.stream();
        Collections.addAll(list, "hello", " ", "world");
        stream.forEach(System.out::print);
    }
}
