package eu.javaspecialists.tjsn.issue223;

import java.util.stream.*;

public class PrintlnFun {
  public static void main(String... args) {
    synchronized (System.out) {
      System.out.println("Hello World");
      IntStream.range(0, 4).parallel().
          forEach(System.out::println);
    }
  }
}
