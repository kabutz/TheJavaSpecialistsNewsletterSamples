package eu.javaspecialists.tjsn.issue188;

import org.jpatterns.gof.*;

/**
 * This special executor guarantees that the call() method of the
 * task parameter is invoked in turns by two threads.  There is
 * probably no practical application for this class, except as a
 * learning experience.
 */
@TemplateMethodPattern.AbstractClass
public abstract class Interlocker {
    @TemplateMethodPattern.PrimitiveOperation
    protected abstract Runnable[] getRunnables(InterlockTask task);

    @TemplateMethodPattern.TemplateMethod
    public final <T> T execute(InterlockTask<T> task)
            throws InterruptedException {
        Runnable[] jobs = getRunnables(task);
        Thread[] threads = new Thread[jobs.length];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(jobs[i]);
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return task.get();
    }
}
