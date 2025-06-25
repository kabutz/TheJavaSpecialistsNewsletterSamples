package eu.javaspecialists.tjsn.issue275;

import java.util.function.*;

public class BeachDistinctify {
    public static void main(String... args) {
        EnhancedStream.of("Kalathas", "Stavros", "STAVROS",
                        "marathi", "kalathas", "baLos", "Balos")
                .distinct(HASH_CODE, EQUALS, MERGE)
                .forEach(System.out::println);
    }

    // case insensitive hashCode() and equals()
    public static final
    ToIntFunction<String> HASH_CODE =
            s -> s.toUpperCase().hashCode();
    public static final
    BiPredicate<String, String> EQUALS =
            (s1, s2) ->
                    s1.toUpperCase().equals(s2.toUpperCase());

    // keep the string with the highest total ascii value
    public static final
    BinaryOperator<String> MERGE =
            (s1, s2) ->
                    s1.chars().sum() < s2.chars().sum() ? s2 : s1;
}
