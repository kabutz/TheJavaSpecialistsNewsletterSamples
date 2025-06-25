package eu.javaspecialists.tjsn.issue320;

import java.lang.management.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class YieldALot {
    public static void main(String... args) {
        var timer = Executors.newSingleThreadScheduledExecutor();
        var running = new AtomicBoolean(true);
        timer.schedule(() -> running.set(false),
                1, TimeUnit.SECONDS);

        var tmb = ManagementFactory.getThreadMXBean();
        var cpu = tmb.getCurrentThreadCpuTime();
        var usr = tmb.getCurrentThreadUserTime();

        var counter = 0;
        while (running.get() && ++counter > 0) Thread.yield();

        cpu = tmb.getCurrentThreadCpuTime() - cpu;
        usr = tmb.getCurrentThreadUserTime() - usr;

        System.out.printf("CPU time    = %,d%n", cpu);
        System.out.printf("User time   = %,d%n", usr);
        System.out.printf("System time = %,d%n", cpu - usr);
        System.out.printf("counter     = %,d%n", counter);
        timer.shutdown();
    }
}
