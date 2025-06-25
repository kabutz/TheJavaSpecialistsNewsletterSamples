package eu.javaspecialists.tjsn.issue288;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Spliterator.*;

public class WhatHasWeaklyConsistentIteration {
  public static void main(String... args) {
    List<Collection<?>> collections = List.of(
        new ArrayBlockingQueue<>(10),
        new ArrayDeque<>(),
        new ArrayList<>(),
        Collections.singleton(42),
        Collections.singletonList(42),
        Collections.emptyList(),
        Collections.emptySet(),
        Arrays.asList(1,2,3),
        ConcurrentHashMap.newKeySet(),
        new ConcurrentLinkedDeque<>(),
        new ConcurrentLinkedQueue<>(),
        new ConcurrentSkipListSet<>(),
        new CopyOnWriteArrayList<>(),
        new CopyOnWriteArraySet<>(),
        new DelayQueue<>(),
        new HashSet<>(),
        new LinkedBlockingDeque<>(),
        new LinkedBlockingQueue<>(),
        new LinkedHashSet<>(),
        new LinkedList<>(),
        new LinkedTransferQueue<>(),
        List.of(),
        new PriorityBlockingQueue<>(),
        new PriorityQueue<>(),
        Set.of(),
        new Stack<>(),
        new TreeSet<>(),
        new Vector<>()
    );

    Predicate<Collection<?>> concurrent =
        c -> c.spliterator().hasCharacteristics(CONCURRENT);
    Predicate<Collection<?>> immutable =
        c -> c.spliterator().hasCharacteristics(IMMUTABLE);
    Consumer<Collection<?>> printer =
        c -> System.out.println("\t" + c.getClass().getName());

    System.out.println("Weakly-Consistent (CONCURRENT)");
    collections.stream()
        .filter(concurrent)
        .forEach(printer);
    System.out.println("Snapshot (IMMUTABLE)");
    collections.stream()
        .filter(immutable)
        .forEach(printer);
    System.out.println("Neither");
    collections.stream()
        .filter(concurrent.or(immutable).negate())
        .forEach(printer);
  }
}
