package eu.javaspecialists.tjsn.issue164;

import java.util.concurrent.atomic.*;

public class MyValue {
    private final int value;
    private final AtomicBoolean finalized;

    public MyValue(int value, AtomicBoolean finalized) {
        this.value = value;
        this.finalized = finalized;
    }

    protected void finalize() throws Throwable {
        System.out.println("MyValue.finalize " + value);
        finalized.set(true);
        super.finalize();
    }
}
