package eu.javaspecialists.tjsn.issue268;

import java.util.*;

public class SortCheck {
    private static final String[] NAMES = {
            "John", "Paul", "Sarah", "Anton", "Antonia", "Peter", "Heinz",
            "Kiriaki", "Kirk", "Maurice", "Simone", "Vuyisile", "Prakash",
            "Yen", "Michelle", "Rabea", "Steve", "Patrick", "Alvaro", "Petros",
            "Charalambos", "Susie", "Rebekka", "Zoran", "Quinton", "Sean"
    };

    private static final int NUMBER_OF_PERSONS = 10000;

    public static void main(String... args) {
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < NUMBER_OF_PERSONS; i++) {
            persons.add(generate());
        }

        while (true) {
            try {
                Collections.sort(persons);
                break;
            } catch (Throwable e) {
                System.err.println("Failed - trying again");
            }
        }
        for (Person person : persons) {
            System.out.println(person);
        }
    }

    private static final Random RANDOM = new Random();

    private static Person generate() {
        String name = NAMES[RANDOM.nextInt(NAMES.length)];
        if (RANDOM.nextBoolean()) {
            return new Person(name);
        } else {
            return new Programmer(name, (short) (RANDOM.nextInt(80) + 120));
        }
    }
}
