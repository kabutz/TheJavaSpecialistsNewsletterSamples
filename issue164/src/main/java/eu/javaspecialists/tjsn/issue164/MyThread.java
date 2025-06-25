package eu.javaspecialists.tjsn.issue164;

import java.util.concurrent.atomic.*;

public class MyThread extends Thread {
    private final AtomicBoolean finalized;

    public MyThread(Runnable target, AtomicBoolean finalized) {
        super(target);
        this.finalized = finalized;
    }

    protected void finalize() throws Throwable {
        System.out.println("MyThread.finalize");
        finalized.set(true);
        super.finalize();
    }
}