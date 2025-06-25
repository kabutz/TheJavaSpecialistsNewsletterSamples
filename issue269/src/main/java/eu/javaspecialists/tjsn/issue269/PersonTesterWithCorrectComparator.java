package eu.javaspecialists.tjsn.issue269;

import java.util.*;
import java.util.stream.*;

public class PersonTesterWithCorrectComparator {
    public static void main(String... args) {
        Person[] people = {
                new Programmer("Aaron", (short) 130),
                new Person("Adolf"),
                new Programmer("Brian", (short) 180),
                new Person("Brian"),
                new Programmer("Cedric", (short) 120),
                new Programmer("Cedric", (short) 120),
                new Programmer("Zoran", (short) 200),
        };

        Comparator<Person> comparator = (p1, p2) -> {
            if (p1 instanceof Programmer) {
                if (p2 instanceof Programmer)
                    return p1.compareTo(p2);
                else
                    return -1;
            }

            if (p2 instanceof Programmer)
                return 1;

            return p1.compareTo(p2);
        };
        CompareTester.test(people, comparator);

        Arrays.sort(people, comparator);
        Stream.of(people).forEach(System.out::println);
    }
}
