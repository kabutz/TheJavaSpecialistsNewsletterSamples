package eu.javaspecialists.tjsn.issue317;

import javax.management.*;
import java.lang.management.*;

public class DiagnosticCommand {
    public static void gcRun() {
        try {
            var server = ManagementFactory.getPlatformMBeanServer();
            var name = new ObjectName(
                    "com.sun.management:type=DiagnosticCommand");
            server.invoke(
                    name,
                    "gcRun",
                    new Object[0], new String[0]);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
