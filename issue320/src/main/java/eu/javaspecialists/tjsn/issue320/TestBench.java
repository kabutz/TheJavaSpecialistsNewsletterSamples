package eu.javaspecialists.tjsn.issue320;

import java.lang.management.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class TestBench {
    private static final String BENCH_THREAD_NAME = "TestBench-";
    private final Thread.Builder builder;
    private final int numberOfThreads;
    private final boolean yielding;
    private final LongAdder progress = new LongAdder();
    private final LongAdder allEvenNumbers = new LongAdder();
    private final AtomicBoolean running = new AtomicBoolean(true);

    public TestBench(Thread.Builder builder,
                     int numberOfThreads,
                     boolean yielding) {
        this.builder = builder;
        this.numberOfThreads = numberOfThreads;
        this.yielding = yielding;
    }

    /**
     * We attempt to count all the even positive numbers.
     * However, since we increment by 2, we overflow over
     * Integer.MAX_VALUE and thus this loop will never end.
     */
    private void countEvenNumbers() {
        var evenNumbers = 0L;
        for (int i = 0; i < Integer.MAX_VALUE && running.get(); i += 2) {
            evenNumbers++;
            if (yielding) Thread.yield();
            if (i % 100_000 == 99_998)
                progress.increment();
        }
        allEvenNumbers.add(evenNumbers);
    }

    /**
     * We launch all our test threads,
     */
    public void runExperiment() {
        System.out.println("Starting experiment with builder " +
                builder.getClass().getSimpleName() +
                ", " + numberOfThreads +
                " thread" + (numberOfThreads == 1 ? "" : "s") +
                " and " + (yielding ? "" : "no ") +
                "yielding");
        var timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(this::printCPUTimes,
                1, 1, TimeUnit.SECONDS);
        timer.schedule(() -> {
            running.set(false);
            timer.shutdownNow();
        }, 10300, TimeUnit.MILLISECONDS);
        var threads = new Thread[numberOfThreads];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = builder.name(BENCH_THREAD_NAME + i)
                    .start(this::countEvenNumbers);
            System.out.println("Launched thread " + threads[i]);
        }
        try {
            builder.start(() ->
                    System.out.println("Launched all threads")
            ).join();
            for (var thread : threads) thread.join();
        } catch (InterruptedException e) {
            throw new CancellationException("interrupted");
        }
        System.out.printf("allEvenNumbers = %,d%n",
                allEvenNumbers.longValue());
    }

    private static final ThreadMXBean tmb =
            ManagementFactory.getThreadMXBean();

    private final String carrierThreadPattern =
            discoverCarrierThreadPattern();

    private String discoverCarrierThreadPattern() {
        var threadString = CompletableFuture.supplyAsync(
                        () -> Thread.currentThread().toString(),
                        Executors.newVirtualThreadPerTaskExecutor())
                .join();
        var lastAtIndex = threadString.lastIndexOf('@');
        if (lastAtIndex < 0) throw new IllegalStateException();
        var lastMinusIndex = threadString.lastIndexOf('-');
        if (lastMinusIndex < 0) throw new IllegalStateException();
        return threadString.substring(
                lastAtIndex + 1, lastMinusIndex + 1);

    }

    private final Map<Long, Time> history = new ConcurrentHashMap<>();

    private record Time(long cpu, long usr) {
    }

    private static final Time ZERO_TIME = new Time(0, 0);

    private void printCPUTimes() {
        // We are going to search for platform threads that are
// either carrier threads or raw TestBench-* threads.
        long totalCpuTime = 0, totalUserTime = 0, totalSysTime = 0;
        for (var tid : tmb.getAllThreadIds()) {
            var info = tmb.getThreadInfo(tid);
            if (isWatched(info)) {
                var prev = history.getOrDefault(tid, ZERO_TIME);
                var curr = new Time(
                        tmb.getThreadCpuTime(tid),
                        tmb.getThreadUserTime(tid)
                );
                history.put(tid, curr);
                var cpuTime = toMs(curr.cpu() - prev.cpu());
                if (cpuTime > 0) {
                    var userTime = toMs(curr.usr() - prev.usr());
                    var sysTime = cpuTime - userTime;
                    totalCpuTime += cpuTime;
                    totalUserTime += userTime;
                    totalSysTime += sysTime;
                    System.out.printf("%s\tcpu=%d\tusr=%d\tsys=%d%n",
                            info.getThreadName(),
                            cpuTime, userTime, sysTime);
                }
            }
        }
        System.out.printf("Total\tcpu=%d\tusr=%d\tsys=%d\tprogress=%d%n",
                totalCpuTime, totalUserTime, totalSysTime,
                progress.longValue());
        System.out.println();
    }

    private boolean isWatched(ThreadInfo info) {
        var threadName = info.getThreadName();
        return threadName.startsWith(carrierThreadPattern) ||
                threadName.startsWith(BENCH_THREAD_NAME);
    }

    private long toMs(long nanos) {
        return nanos / 1_000_000;
    }
}
