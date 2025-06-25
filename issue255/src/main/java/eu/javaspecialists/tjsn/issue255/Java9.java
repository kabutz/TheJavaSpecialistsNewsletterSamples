package eu.javaspecialists.tjsn.issue255;

import java.util.*;

public class Java9 {
    public static void main(String... args) {
        for (String arg : args) {
            System.out.println(arg);
        }

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            System.out.println(arg);
        }

        List<String> strings = new ArrayList<>();
        strings.add("hello");
        for (String string : strings) {
        }

        List<String> explicit = new ArrayList<>();

        String name = "Hello";
        name += " World";
        System.out.println("name = " + name);

        int var = 42; // <-- hmmm, wonder what will happen?
    }
}
