package eu.javaspecialists.tjsn.issue297;

import javax.management.*;
import java.lang.management.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
 * @author Dr Heinz M. Kabutz heinz@javaspecialists.eu
 */
public final class ForkJoinPoolBench {
    /**
     * The key to this bench is the ThreadFactory, which measures
     * the cpu time, user time and memory allocation of each thread
     * that is created in the common ForkJoinPool. The number of
     * threads might increase temporarily because of the
     * ManagedBlocker, and we never remove unused threads again.
     */
    public static class ThreadFactory
            implements ForkJoinPool.ForkJoinWorkerThreadFactory {
        public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
            var thread =
                    ForkJoinPool.defaultForkJoinWorkerThreadFactory
                            .newThread(pool);
            bench.addCounters(thread);
            return thread;
        }
    }

    /**
     * We run the task in our bench, measuring user time, cpu time,
     * real time and bytes allocated.
     */
    public static void test(Runnable task, StatsListener listener) {
        bench.test0(task, listener);
    }

    /**
     * Class to simplify the ConcurrentHashMap usage.
     */
    private static class MeasureMap extends
            ConcurrentHashMap<Thread, AtomicLong> {
    }

    private final MeasureMap userTime = new MeasureMap();
    private final MeasureMap cpuTime = new MeasureMap();
    private final MeasureMap allocation = new MeasureMap();

    /**
     * Interface to simplify the ToLongFunction code.
     */
    @FunctionalInterface
    private interface ExtractorFunction {
        long extract(Thread thread);
    }

    private ForkJoinPoolBench() {
    }

    /**
     * Only one thread at a time can run the test.
     */
    private static final Object TEST_MONITOR = new Object();

    private void test0(Runnable task, StatsListener listener) {
        LongSummaryStatistics userStats, cpuStats, memStats;
        long realTime;
        synchronized (TEST_MONITOR) {
            addCounters(Thread.currentThread());
            try {
                resetAllCounters();
                realTime = System.nanoTime();
                try {
                    task.run();
                } finally {
                    memStats = getStats(allocation, MEM_FUNCTION);
                    realTime = System.nanoTime() - realTime;
                    userStats = getStats(userTime, USER_TIME_FUNCTION);
                    cpuStats = getStats(cpuTime, CPU_TIME_FUNCTION);
                }
            } finally {
                removeCounters(Thread.currentThread());
            }
        }
        listener.result(realTime, userStats, cpuStats, memStats);
    }

    private LongSummaryStatistics getStats(
            MeasureMap map,
            ExtractorFunction extractorFunction) {
        map.forEach((key, value) -> {
            long after = extractorFunction.extract(key);
            long before = value.get();
            value.set(after - before);
        });
        return map.values()
                .stream()
                .mapToLong(AtomicLong::get)
                .summaryStatistics();
    }

    private void addCounters(Thread thread) {
        add(userTime, thread, USER_TIME_FUNCTION);
        add(cpuTime, thread, CPU_TIME_FUNCTION);
        add(allocation, thread, MEM_FUNCTION);
    }

    private void removeCounters(Thread thread) {
        userTime.remove(thread);
        cpuTime.remove(thread);
        allocation.remove(thread);
    }

    private void add(MeasureMap map,
                     Thread thread,
                     ExtractorFunction extractor) {
        map.put(thread, new AtomicLong(extractor.extract(thread)));
    }

    private void resetAllCounters() {
        resetCounter(userTime, USER_TIME_FUNCTION);
        resetCounter(cpuTime, CPU_TIME_FUNCTION);
        resetCounter(allocation, MEM_FUNCTION);
    }

    private void resetCounter(MeasureMap map,
                              ExtractorFunction extractor) {
        map.forEach((thread, value) ->
                value.set(extractor.extract(thread)));
    }

    private static long threadAllocatedBytes(Thread thread) {
        try {
            return (long) mBeanServer.invoke(name,
                    "getThreadAllocatedBytes",
                    new Object[]{thread.getId()},
                    SIGNATURE);
        } catch (JMException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static final ThreadMXBean tmb =
            ManagementFactory.getThreadMXBean();
    private static final ExtractorFunction USER_TIME_FUNCTION =
            thread -> tmb.getThreadUserTime(thread.getId());
    private static final ExtractorFunction CPU_TIME_FUNCTION =
            thread -> tmb.getThreadCpuTime(thread.getId());
    private static final ExtractorFunction MEM_FUNCTION =
            ForkJoinPoolBench::threadAllocatedBytes;
    private static final String[] SIGNATURE =
            {long.class.getName()};
    private static final MBeanServer mBeanServer =
            ManagementFactory.getPlatformMBeanServer();
    private static final ObjectName name;
    private static final ForkJoinPoolBench bench;

    static {
        System.setProperty("java.util.concurrent.ForkJoinPool" +
                ".common.threadFactory", ThreadFactory.class.getName());
        if (!(ForkJoinPool.commonPool()
                .getFactory() instanceof ThreadFactory))
            throw new IllegalStateException(
                    "Common pool thread factory should be a " +
                            ThreadFactory.class.getName());
        try {
            name = new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME);
        } catch (MalformedObjectNameException e) {
            throw new ExceptionInInitializerError(e);
        }
        bench = new ForkJoinPoolBench();
    }

    public interface StatsListener {
        void result(long realTime,
                    LongSummaryStatistics userTimeStats,
                    LongSummaryStatistics cpuTimeStats,
                    LongSummaryStatistics allocationStats);
    }
}
