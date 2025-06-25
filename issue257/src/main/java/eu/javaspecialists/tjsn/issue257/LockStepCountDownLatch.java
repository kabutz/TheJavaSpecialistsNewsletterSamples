package eu.javaspecialists.tjsn.issue257;

import java.util.concurrent.*;

import static java.util.concurrent.Executors.*;

public class LockStepCountDownLatch extends LockStepExample {
    public static void main(String... args) {
        LockStepCountDownLatch lse = new LockStepCountDownLatch();
        ExecutorService pool = newFixedThreadPool(TASKS_PER_BATCH);
        for (int batch = 0; batch < BATCHES; batch++) {
            // We need a new CountDownLatch per batch, since they
            // cannot be reset to their initial value
            CountDownLatch latch = new CountDownLatch(TASKS_PER_BATCH);
            for (int i = 0; i < TASKS_PER_BATCH; i++) {
                int batchNumber = batch + 1;
                pool.submit(() -> lse.task(latch, batchNumber));
            }
        }
        pool.shutdown();
    }

    public void task(CountDownLatch latch, int batch) {
        latch.countDown();
        boolean interrupted = Thread.interrupted();
        while (true) {
            try {
                latch.await();
                break;
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
        if (interrupted) Thread.currentThread().interrupt();
        doTask(batch);
    }
}
