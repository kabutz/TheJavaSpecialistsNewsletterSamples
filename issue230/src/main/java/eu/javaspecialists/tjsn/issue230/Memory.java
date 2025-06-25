package eu.javaspecialists.tjsn.issue230;

import javax.management.*;
import java.lang.management.*;

public class Memory {
    public static long threadAllocatedBytes() {
        try {
            return (Long) ManagementFactory.getPlatformMBeanServer()
                    .invoke(
                            new ObjectName(
                                    ManagementFactory.THREAD_MXBEAN_NAME),
                            "getThreadAllocatedBytes",
                            new Object[]{Thread.currentThread().getId()},
                            new String[]{long.class.getName()}
                    );
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
