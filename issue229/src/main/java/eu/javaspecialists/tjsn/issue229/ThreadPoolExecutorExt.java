package eu.javaspecialists.tjsn.issue229;

import java.util.concurrent.*;

public class ThreadPoolExecutorExt extends ThreadPoolExecutor {
    private final ThreadLocalChangeListener listener;

    /* Bunch of constructors following - you can ignore those */
    public ThreadPoolExecutorExt(
            int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, ThreadLocalChangeListener.EMPTY);
    }

    public ThreadPoolExecutorExt(
            int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit, BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory,
                ThreadLocalChangeListener.EMPTY);
    }

    public ThreadPoolExecutorExt(
            int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit, BlockingQueue<Runnable> workQueue,
            RejectedExecutionHandler handler) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, handler,
                ThreadLocalChangeListener.EMPTY);
    }

    public ThreadPoolExecutorExt(
            int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit, BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory,
            RejectedExecutionHandler handler) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler,
                ThreadLocalChangeListener.EMPTY);
    }

    public ThreadPoolExecutorExt(
            int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit, BlockingQueue<Runnable> workQueue,
            ThreadLocalChangeListener listener) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue);
        this.listener = listener;
    }

    public ThreadPoolExecutorExt(
            int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit, BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory,
            ThreadLocalChangeListener listener) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory);
        this.listener = listener;
    }

    public ThreadPoolExecutorExt(
            int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit, BlockingQueue<Runnable> workQueue,
            RejectedExecutionHandler handler,
            ThreadLocalChangeListener listener) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, handler);
        this.listener = listener;
    }

    public ThreadPoolExecutorExt(
            int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit, BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory,
            RejectedExecutionHandler handler,
            ThreadLocalChangeListener listener) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);
        this.listener = listener;
    }

    /* The interest bit of this class is below ... */
    private static final ThreadLocal<ThreadLocalCleaner> local =
            new ThreadLocal<>();

    protected void beforeExecute(Thread t, Runnable r) {
        assert t == Thread.currentThread();
        local.set(new ThreadLocalCleaner(listener));
    }

    protected void afterExecute(Runnable r, Throwable t) {
        ThreadLocalCleaner cleaner = local.get();
        local.remove();
        cleaner.cleanup();
    }
}
