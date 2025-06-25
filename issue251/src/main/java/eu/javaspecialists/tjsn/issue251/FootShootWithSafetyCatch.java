package eu.javaspecialists.tjsn.issue251;

import java.util.*;

import static java.util.stream.Collectors.*;

public class FootShootWithSafetyCatch {
    public static void main(String... args) {
        List<String> names = Collections.checkedList(
                new ArrayList<>(), String.class);
        Collections.addAll(names, "John", "Anton", "Heinz");
        List huh = names;
        List<Integer> numbers = huh;
        numbers.add(42);
        System.out.println(names.stream().collect(joining("+")));
    }
}
