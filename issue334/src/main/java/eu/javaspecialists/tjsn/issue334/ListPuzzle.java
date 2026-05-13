package eu.javaspecialists.tjsn.issue334;

import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.function.*;

public abstract class ListPuzzle {
    void run(Supplier<? extends List<Integer>> supplier) {
        var tmb = ManagementFactory.getThreadMXBean();
        var tim = System.nanoTime();
        var cpu = tmb.getCurrentThreadCpuTime();
        var usr = tmb.getCurrentThreadUserTime();
        try {
            var list = supplier.get();
            System.out.println(list.getClass() + ":");
            for (var i = 0; i < 100_000_000; i++) {
                list.add(42);
            }
        } finally {
            tim = System.nanoTime() - tim;
            usr = tmb.getCurrentThreadUserTime() - usr;
            cpu = tmb.getCurrentThreadCpuTime() - cpu;
            System.out.printf("tim=%d,", (tim / 1000000));
            System.out.printf("cpu=%d,", (cpu / 1000000));
            System.out.printf("usr=%d%n", (usr / 1000000));
        }
    }
}
