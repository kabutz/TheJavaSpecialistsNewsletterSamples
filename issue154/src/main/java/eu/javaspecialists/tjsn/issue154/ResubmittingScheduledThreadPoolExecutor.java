package eu.javaspecialists.tjsn.issue154;

import java.util.*;
import java.util.concurrent.*;

public class ResubmittingScheduledThreadPoolExecutor
        extends ScheduledThreadPoolExecutor {
    /**
     * Default exception handler, always reschedules
     */
    private static final ScheduledExceptionHandler NULL_HANDLER =
            new ScheduledExceptionHandler() {
                public boolean exceptionOccurred(Throwable e) {
                    return true;
                }
            };
    private final Map<Object, FixedRateParameters> runnables =
            new IdentityHashMap<Object, FixedRateParameters>();
    private final ScheduledExceptionHandler handler;

    /**
     * @param reschedule when an exception causes a task to be
     *                   aborted, reschedule it and notify the
     *                   exception listener
     */
    public ResubmittingScheduledThreadPoolExecutor(int poolSize) {
        this(poolSize, NULL_HANDLER);
    }

    public ResubmittingScheduledThreadPoolExecutor(
            int poolSize, ScheduledExceptionHandler handler) {
        super(poolSize);
        this.handler = handler;
    }

    private class FixedRateParameters {
        private Runnable command;
        private long period;
        private TimeUnit unit;

        /**
         * We do not need initialDelay, since we can set it to period
         */
        public FixedRateParameters(Runnable command, long period,
                                   TimeUnit unit) {
            this.command = command;
            this.period = period;
            this.unit = unit;
        }
    }

    public ScheduledFuture<?> scheduleAtFixedRate(
            Runnable command, long initialDelay, long period,
            TimeUnit unit) {
        ScheduledFuture<?> future = super.scheduleAtFixedRate(
                command, initialDelay, period, unit);
        runnables.put(future,
                new FixedRateParameters(command, period, unit));
        return future;
    }

    protected void afterExecute(Runnable r, Throwable t) {
        ScheduledFuture future = (ScheduledFuture) r;
        // future.isDone() is always false for scheduled tasks,
        // unless there was an exception
        if (future.isDone()) {
            try {
                future.get();
            } catch (ExecutionException e) {
                Throwable problem = e.getCause();
                FixedRateParameters parms = runnables.remove(r);
                if (problem != null && parms != null) {
                    boolean resubmitThisTask =
                            handler.exceptionOccurred(problem);
                    if (resubmitThisTask) {
                        scheduleAtFixedRate(parms.command, parms.period,
                                parms.period, parms.unit);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
