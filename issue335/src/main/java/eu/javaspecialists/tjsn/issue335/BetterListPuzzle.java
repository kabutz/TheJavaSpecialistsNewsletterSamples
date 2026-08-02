package eu.javaspecialists.tjsn.issue335;

import eu.javaspecialists.tjsn.issue334.ListPuzzle;

import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.function.*;

public abstract class BetterListPuzzle extends ListPuzzle {
    public int warmups() { return 10; }
    public int repeats() { return 30; }
    public void run(
            Supplier<? extends List<Integer>> supplier) {
        System.out.println("Warmup: 10x");
        for (var w = 0; w < warmups(); w++) {
            super.run(supplier);
        }
        System.out.println("Actual runs: 30x");
        var tmb = ManagementFactory.getThreadMXBean();
        var tim = System.nanoTime();
        var cpu = tmb.getCurrentThreadCpuTime();
        var usr = tmb.getCurrentThreadUserTime();
        try {
            for (var r = 0; r < repeats(); r++) {
                super.run(supplier);
            }
        } finally {
            tim = System.nanoTime() - tim;
            usr = tmb.getCurrentThreadUserTime() - usr;
            cpu = tmb.getCurrentThreadCpuTime() - cpu;
            System.out.println("Total times:");
            System.out.printf("tim=%d,", (tim / 1000000));
            System.out.printf("cpu=%d,", (cpu / 1000000));
            System.out.printf("usr=%d%n", (usr / 1000000));
        }
    }
}
