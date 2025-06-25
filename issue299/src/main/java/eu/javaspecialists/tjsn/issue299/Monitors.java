package eu.javaspecialists.tjsn.issue299;

import java.lang.management.*;

public class Monitors {
    private static final ThreadMXBean tmb =
            ManagementFactory.getThreadMXBean();

    private Monitors() {
    }

    public static MonitorInfo[] findLockedMonitors() {
        long[] ids = {Thread.currentThread().getId()};
        var threadInfo = tmb.getThreadInfo(ids, true, false)[0];
        return threadInfo.getLockedMonitors();
    }
}
