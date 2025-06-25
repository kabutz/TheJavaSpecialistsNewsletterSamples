package eu.javaspecialists.tjsn.issue233;

import java.util.stream.*;

public class PsychiatristLambda {
    public static void main(String... args) {
        Runnable job = new MultiplePersonalities().create();
        Stream.of(job.getClass().getInterfaces())
                .forEach(clazz -> System.out.println(clazz.getName()));
    }
}
