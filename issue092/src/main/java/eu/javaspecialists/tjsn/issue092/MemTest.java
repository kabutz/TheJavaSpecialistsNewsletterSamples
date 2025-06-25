package eu.javaspecialists.tjsn.issue092;

import java.util.*;

public class MemTest {
    public static void main(String... args) {
        MemoryWarningSystem.setPercentageUsageThreshold(0.6);

        MemoryWarningSystem mws = new MemoryWarningSystem();
        mws.addListener(new MemoryWarningSystem.Listener() {
            public void memoryUsageLow(long usedMemory, long maxMemory) {
                System.out.println("Memory usage low!!!");
                double percentageUsed = ((double) usedMemory) / maxMemory;
                System.out.println("percentageUsed = " + percentageUsed);
                MemoryWarningSystem.setPercentageUsageThreshold(0.8);
            }
        });

        Collection<byte[]> numbers = new LinkedList<byte[]>();
        while (true) {
            numbers.add(new byte[10 * 1024]);
        }
    }
}
