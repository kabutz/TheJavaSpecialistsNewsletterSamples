package eu.javaspecialists.tjsn.issue239;

import javax.management.*;
import java.lang.management.*;

/**
 * Reduced version of the ByteWatcher, described here:
 * https://www.javaspecialists.eu/archive/Issue232.html
 */
public class ByteWatcher {
    private static final String GET_THREAD_ALLOCATED_BYTES =
            "getThreadAllocatedBytes";
    private static final String[] SIGNATURE =
            new String[]{long.class.getName()};
    private static final MBeanServer mBeanServer;
    private static final ObjectName name;

    private final Object[] PARAMS;
    private final long MEASURING_COST_IN_BYTES; // usually 336
    private final long tid;

    private long allocated = 0;

    static {
        try {
            name = new ObjectName(
                    ManagementFactory.THREAD_MXBEAN_NAME);
            mBeanServer = ManagementFactory.getPlatformMBeanServer();
        } catch (MalformedObjectNameException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public ByteWatcher() {
        this.tid = Thread.currentThread().getId();
        PARAMS = new Object[]{tid};

        long calibrate = threadAllocatedBytes();
        // calibrate
        for (int repeats = 0; repeats < 10; repeats++) {
            for (int i = 0; i < 10_000; i++) {
                // run a few loops to allow for startup anomalies
                calibrate = threadAllocatedBytes();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        MEASURING_COST_IN_BYTES = threadAllocatedBytes() - calibrate;
        reset();
    }

    public void reset() {
        allocated = threadAllocatedBytes();
    }

    private long threadAllocatedBytes() {
        try {
            return (long) mBeanServer.invoke(
                    name,
                    GET_THREAD_ALLOCATED_BYTES,
                    PARAMS,
                    SIGNATURE
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Calculates the number of bytes allocated since the last
     * reset().
     */
    public long calculateAllocations() {
        long mark1 = ((threadAllocatedBytes() -
                MEASURING_COST_IN_BYTES) - allocated);
        return mark1;
    }
}
