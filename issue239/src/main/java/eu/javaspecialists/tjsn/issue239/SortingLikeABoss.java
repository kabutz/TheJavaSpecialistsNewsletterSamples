package eu.javaspecialists.tjsn.issue239;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class SortingLikeABoss {
    public static <E, R extends List<E>> R parallelSort(
            List<E> list,
            Function<List<E>, R> listConstructor) {
        return listConstructor.apply(
                list.parallelStream()
                        .sorted()
                        .collect(Collectors.toList()));
    }
}
