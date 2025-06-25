package eu.javaspecialists.tjsn.issue299;

import java.lang.management.*;
import java.time.*;

// Use these JVM flags:
// -XX:+UnlockDiagnosticVMOptions
// -XX:DiagnoseSyncOnValueBasedClasses=0..2
public class LockingOnValueBasedClasses {
    public static void main(String... args) {
        synchronize(42);

        synchronize("Hello");

        synchronize(LocalDate.now());
    }

    private static void synchronize(Object o) {
        synchronized (o) {
            System.out.println("We now hold the lock of " + o);
            for (MonitorInfo monitor : Monitors.findLockedMonitors()) {
                System.out.println("\t" + monitor);
            }
        }
    }
}
