package eu.javaspecialists.tjsn.issue251;

import java.util.*;

public class FootShootJava5 {
    public static void main(String... args) {
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "John", "Anton", "Heinz");
        List huh = names;
        List<Integer> numbers = huh;
        numbers.add(42);
    }
}
