package eu.javaspecialists.tjsn.issue185;

import java.util.*;

public class ResurrectingTheDead {
    private static final Map<Zombie, Zombie> lruCache =
            new LinkedHashMap<Zombie, Zombie>(1000, 0.75f, true) {
                protected boolean removeEldestEntry(
                        Map.Entry<Zombie, Zombie> eldest) {
                    return size() > 1000;
                }
            };

    public static void main(String... args) throws Exception {
        while (true) {
            // We create a bunch of zombies, which will take at least
            // 24 bytes each on a 64 bit machine
            for (int i = 0; i < 100 * 1000; i++) {
                new Zombie();
            }

            System.out.printf("UsedMem before gc: %dmb%n",
                    memoryUsedInMB());
            // We make sure that the objects are all collected
            for (int i = 0; i < 10; i++) {
                System.gc();
                Thread.sleep(10);
            }
            // This should be 0 or close to that
            System.out.printf("UsedMem after gc: %dmb%n%n",
                    memoryUsedInMB());
        }
    }

    private static long memoryUsedInMB() {
        return (Runtime.getRuntime().totalMemory() -
                Runtime.getRuntime().freeMemory()) / 1024 / 1024;
    }

    private static class Zombie {
        // Since finalized is being accessed from multiple threads,
        // we need to make it volatile
        private volatile boolean finalized = false;

        protected void finalize() throws Throwable {
            if (finalized) System.err.println("Finalized twice");
            finalized = true;
            lruCache.put(this, this); // we let 'this' reference escape
            super.finalize();
        }
    }
}
