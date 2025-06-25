package eu.javaspecialists.tjsn.issue239;

import java.io.*;
import java.lang.management.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class ListSorting {
    private static final ByteWatcher byteWatcher =
            new ByteWatcher();

    public static void main(String... args) throws IOException {
        for (int i = 0; i < 10; i++) {
            testAll();
            System.out.println();
        }
    }

    private static void testAll() {
        for (int size = 100_000; size <= 10_000_000; size *= 10) {
            List<Double> jumble =
                    ThreadLocalRandom.current()
                            .doubles(size)
                            .boxed()
                            .collect(Collectors.toList());
            test(ArrayList::new, jumble);
            test(LinkedList::new, jumble);
            test(Vector::new, jumble);
            test(CopyOnWriteArrayList::new, jumble);
            test(doubles ->
                    Arrays.asList(
                            jumble.stream().toArray(Double[]::new)
                    ), jumble);
        }
    }

    private static void test(
            UnaryOperator<List<Double>> listConstr,
            List<Double> list) {
        sortOld(listConstr.apply(list));
        sortNew(listConstr.apply(list));
    }

    private static void sortOld(List<Double> list) {
        measureSort("Old", list, () -> sort(list));
    }

    private static void sortNew(List<Double> list) {
        measureSort("New", list, () -> list.sort(null));
    }

    private static final ThreadMXBean tmbean =
            ManagementFactory.getThreadMXBean();

    private static void measureSort(String type,
                                    List<Double> list,
                                    Runnable sortJob) {
        try {
            long time = tmbean.getCurrentThreadUserTime();
            byteWatcher.reset();
            sortJob.run();
            long bytes = byteWatcher.calculateAllocations();
            time = tmbean.getCurrentThreadUserTime() - time;
            time = TimeUnit.MILLISECONDS.convert(
                    time, TimeUnit.NANOSECONDS);
            System.out.printf(
                    "%s sort %s %,3d in %dms and bytes %s%n",
                    type,
                    list.getClass().getName(),
                    list.size(),
                    time,
                    Memory.format(bytes, Memory.BYTES, 2));
        } catch (UnsupportedOperationException ex) {
            System.out.println("Old sort: Cannot sort " +
                    list.getClass().getName() + " " + ex);
        }
    }

    /**
     * {@linkplain java.util.Collections#sort Copied from Java 7}
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <E> void sort(List<E> list) {
        Object[] a = list.toArray();
        Arrays.sort(a);
        ListIterator<E> i = list.listIterator();
        for (Object e : a) {
            i.next();
            i.set((E) e);
        }
    }
}
