package eu.javaspecialists.tjsn.issue164;

import java.util.concurrent.atomic.*;

public class MyThreadLocal<T> extends ThreadLocal<T> {
    private final AtomicBoolean finalized;

    public MyThreadLocal(AtomicBoolean finalized) {
        this.finalized = finalized;
    }

    protected void finalize() throws Throwable {
        System.out.println("MyThreadLocal.finalize");
        finalized.set(true);
        super.finalize();
    }
}