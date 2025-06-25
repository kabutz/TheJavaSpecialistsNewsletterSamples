package eu.javaspecialists.tjsn.issue255;

import java.util.*;

public class Java10 {
    public static void main(String... args) {
        for (var arg : args) {
            System.out.println(arg);
        }

        for (var i = 0; i < args.length; i++) {
            var arg = args[i];
            System.out.println(arg);
        }

        var strings = new ArrayList<String>();
        strings.add("hello");
        for (String string : strings) {
        }

        List<String> explicit = new ArrayList<>();

        var name = "Hello";
        name += " World";
        System.out.println("name = " + name);

        var var = 42; // <-- this works
    }
}
