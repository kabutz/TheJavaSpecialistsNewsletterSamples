package eu.javaspecialists.tjsn.issue299;

import java.lang.management.*;
import java.util.*;

public class MonitorsDemo {
    public static void main(String... args) {
        printInfo();

        Object lock1 = new Object();
        synchronized (lock1) {
            printInfo();
        }

        Object lock2 = new Object();
        synchronized (lock1) {
            synchronized (lock2) {
                printInfo();
            }
        }
    }

    private static void printInfo() {
        System.out.println("Monitors locked by current thread:");
        MonitorInfo[] monitors = Monitors.findLockedMonitors();
        if (monitors.length == 0) System.out.println("\tnone");
        else Arrays.stream(monitors)
                .forEach(monitor -> System.out.println("\t" + monitor));
        System.out.println();
    }
}
