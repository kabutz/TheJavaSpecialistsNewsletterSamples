package eu.javaspecialists.tjsn.issue257;

import java.util.concurrent.*;

import static java.util.concurrent.Executors.*;

public class LockStepCyclicBarrier extends LockStepExample {
    public static void main(String... args) {
        LockStepCyclicBarrier lse = new LockStepCyclicBarrier();
        ExecutorService pool = newFixedThreadPool(TASKS_PER_BATCH);
        CyclicBarrier barrier = new CyclicBarrier(TASKS_PER_BATCH);
        for (int batch = 0; batch < BATCHES; batch++) {
            for (int i = 0; i < TASKS_PER_BATCH; i++) {
                int batchNumber = batch + 1;
                pool.submit(() -> lse.task(barrier, batchNumber));
            }
        }
        pool.shutdown();
    }

    public void task(CyclicBarrier barrier, int batch) {
        boolean interrupted = Thread.interrupted();
        while (true) {
            try {
                barrier.await();
                break;
            } catch (InterruptedException e) {
                interrupted = true;
            } catch (BrokenBarrierException e) {
                throw new AssertionError(e);
            }
        }
        if (interrupted) Thread.currentThread().interrupt();
        doTask(batch);
    }
}
