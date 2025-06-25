package eu.javaspecialists.tjsn.issue090;

import java.util.*;

public class NewForPerformance {
    public static void main(String... args) {
        // let's look at the performance difference of for construct
        Collection<Integer> numbers = new ArrayList<Integer>(10000);
        for (int i = 0; i < 10000; i++) {
            // I can add an "int" to a collection of Integer, thanks
            // to the autoboxing construct shamelessly copied from C#
            numbers.add((int) Math.random());
        }
        oldFor(numbers);
        newFor(numbers);
    }

    private static void oldFor(final Collection<Integer> numbers) {
        measureIterations("oldFor", new Runnable() {
            public void run() {
                for (Iterator<Integer> it = numbers.iterator(); it.hasNext(); ) {
                    Integer i = it.next();
                }
            }
        });
    }

    private static void newFor(final Collection<Integer> numbers) {
        measureIterations("newFor", new Runnable() {
            public void run() {
                for (Integer i : numbers) ;
            }
        });
    }

    private static void measureIterations(String method, Runnable r) {
        long start = System.currentTimeMillis();
        int iterations = 0;
        while (System.currentTimeMillis() - start <= 2000) {
            r.run();
            iterations++;
        }
        System.out.println(method + ": " + iterations + " in " +
                (System.currentTimeMillis() - start) + "ms");
    }
}
