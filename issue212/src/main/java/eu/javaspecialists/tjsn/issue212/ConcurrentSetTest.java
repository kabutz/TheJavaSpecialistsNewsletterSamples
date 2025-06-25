package eu.javaspecialists.tjsn.issue212;

import java.util.*;
import java.util.concurrent.*;

public class ConcurrentSetTest {
  public static void main(String... args) {
    Set<String> names = Collections.newSetFromMap(
        new ConcurrentHashMap<String, Boolean>()
    );
    names.add("Brian Goetz");
    names.add("Victor Grazi");
    names.add("Heinz Kabutz");
    names.add("Brian Goetz");
    System.out.println("names = " + names);
  }
}
