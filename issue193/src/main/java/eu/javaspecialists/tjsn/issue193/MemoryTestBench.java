package eu.javaspecialists.tjsn.issue193;

public class MemoryTestBench {
    public long calculateMemoryUsage(ObjectFactory factory) {
        Object handle = factory.makeObject();
        long memory = usedMemory();
        handle = null;
        lotsOfGC();
        memory = usedMemory();
        handle = factory.makeObject();
        lotsOfGC();
        return usedMemory() - memory;
    }

    private long usedMemory() {
        return Runtime.getRuntime().totalMemory() -
                Runtime.getRuntime().freeMemory();
    }

    private void lotsOfGC() {
        for (int i = 0; i < 20; i++) {
            System.gc();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void showMemoryUsage(ObjectFactory factory) {
        long mem = calculateMemoryUsage(factory);
        System.out.println(
                factory.getClass().getSimpleName() + " produced " +
                        factory.makeObject().getClass().getSimpleName() +
                        " which took " + mem + " bytes");
    }
}
