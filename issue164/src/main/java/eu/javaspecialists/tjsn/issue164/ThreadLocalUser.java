package eu.javaspecialists.tjsn.issue164;

import java.util.concurrent.atomic.*;

public class ThreadLocalUser {
    private final int num;
    private MyThreadLocal<MyValue> value;
    private final AtomicBoolean finalized;

    public ThreadLocalUser(AtomicBoolean finalized,
                           AtomicBoolean nestedFinalized) {
        this(0, finalized, nestedFinalized);
    }

    public ThreadLocalUser(int num,
                           AtomicBoolean finalized,
                           AtomicBoolean nestedFinalized) {
        this.num = num;
        this.finalized = finalized;
        value = new MyThreadLocal<MyValue>(nestedFinalized);
    }

    protected void finalize() throws Throwable {
        System.out.println("ThreadLocalUser.finalize " + num);
        finalized.set(true);
        super.finalize();
    }

    public void setThreadLocal(MyValue myValue) {
        value.set(myValue);
    }

    public void clear() {
        value.remove();
    }
}
