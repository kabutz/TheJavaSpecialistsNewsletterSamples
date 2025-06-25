package eu.javaspecialists.tjsn.issue233;

import java.util.stream.*;

// --add-exports java.desktop/java.awt.peer=ALL-UNNAMED for SeriouslyDisturbed
public class Psychiatrist {
    public static void main(String... args) {
        Runnable job = new MultiplePersonalities().create();
        // Runnable job = new SeriouslyDisturbed().create();
        Stream.of(job.getClass().getInterfaces())
                .map(Class::getName)
                .forEach(System.out::println);
    }
}
