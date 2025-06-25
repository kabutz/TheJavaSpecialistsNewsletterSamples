package eu.javaspecialists.tjsn.issue188;

import java.util.concurrent.*;

public class SemaphoreInterlocker extends Interlocker {
    private static class Job implements Runnable {
        private final InterlockTask task;
        private final Semaphore first;
        private final Semaphore second;

        public Job(InterlockTask task,
                   Semaphore first, Semaphore second) {
            this.task = task;
            this.first = first;
            this.second = second;
        }

        public void run() {
            while (!task.isDone()) {
                first.acquireUninterruptibly();
                if (task.isDone()) return;
                task.call();
                second.release();
            }
        }
    }

    protected Runnable[] getRunnables(InterlockTask task) {
        Semaphore even = new Semaphore(1);
        Semaphore odd = new Semaphore(0);
        return new Runnable[]{
                new Job(task, even, odd),
                new Job(task, odd, even)
        };
    }
}
