package eu.javaspecialists.tjsn.issue257;

import java.util.concurrent.*;

public abstract class LockStepExample {
    protected static final int TASKS_PER_BATCH = 3;
    protected static final int BATCHES = 5;

    protected final void doTask(int batch) {
        System.out.printf("Task start %d%n", batch);
        int ms = ThreadLocalRandom.current().nextInt(500, 3000);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.printf("Task in batch %d took %dms%n", batch, ms);
    }
}
