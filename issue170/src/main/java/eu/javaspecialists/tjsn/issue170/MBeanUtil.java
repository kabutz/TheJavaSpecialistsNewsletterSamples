package eu.javaspecialists.tjsn.issue170;

import javax.management.*;
import java.lang.management.*;

public class MBeanUtil {
    private static final FinalizerWatcher FINALIZER_WATCHER =
            new FinalizerWatcher();

    public static FinalizerWatcher getFinalizerWatcher() {
        return FINALIZER_WATCHER;
    }

    static {
        try {
            MBeanServer mbs =
                    ManagementFactory.getPlatformMBeanServer();
            mbs.registerMBean(FINALIZER_WATCHER, new ObjectName(
                    "eu.javaspecialists.performance:type=FinalizerWatch"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
