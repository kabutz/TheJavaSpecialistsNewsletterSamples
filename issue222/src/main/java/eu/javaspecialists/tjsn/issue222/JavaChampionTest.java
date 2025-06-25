package eu.javaspecialists.tjsn.issue222;

import java.util.*;

public class JavaChampionTest {
    public static void main(String... args) {
        Map<JavaChampion, String> urls = new HashMap<>();
        urls.put(new JavaChampion("Jack"), "fasterj.com");
        urls.put(new JavaChampion("Kirk"), "kodewerk.com");
        urls.put(new JavaChampion("Heinz"), "javaspecialists.eu");

        urls.forEach((p, u) -> System.out.println(u)); // Java 8

        System.out.println("URL for Kirk: " +
                urls.get(new JavaChampion("Kirk"))); // url == null
    }
}
