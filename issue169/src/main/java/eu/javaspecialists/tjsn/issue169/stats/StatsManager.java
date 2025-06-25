package eu.javaspecialists.tjsn.issue169.stats;

import javax.management.*;
import java.lang.management.*;

public class StatsManager {
    private static final SocketStats socketStats =
            new SocketStats();

    public static SocketStats getSocketStats() {
        return socketStats;
    }

    static {
        try {
            MBeanServer mbs =
                    ManagementFactory.getPlatformMBeanServer();
            mbs.registerMBean(socketStats, new ObjectName(
                    "eu.javaspecialists.stats:type=SocketStats"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
