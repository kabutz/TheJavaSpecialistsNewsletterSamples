package eu.javaspecialists.tjsn.issue271;

import java.lang.management.*;

public class Sleepy {
    public static void main(String... args) throws InterruptedException {
        ThreadMXBean tmb = ManagementFactory.getThreadMXBean();
        long userTime = tmb.getCurrentThreadUserTime();
        long cpuTime = tmb.getCurrentThreadCpuTime();
        long time = System.nanoTime();
        try {
            for (int i = 0; i < 10_000_000; i++) {
//        Thread.yield();
                Thread.sleep(0);
            }
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
        userTime = tmb.getCurrentThreadUserTime() - userTime;
        cpuTime = tmb.getCurrentThreadCpuTime() - cpuTime;
        System.out.printf("cputime = %dms%n", (cpuTime / 1_000_000));
        System.out.printf("usertime = %dms%n", (userTime / 1_000_000));
    }
}
