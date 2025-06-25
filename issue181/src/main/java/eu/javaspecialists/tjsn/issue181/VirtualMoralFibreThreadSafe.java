package eu.javaspecialists.tjsn.issue181;

import java.util.concurrent.locks.*;

public class VirtualMoralFibreThreadSafe extends VirtualMoralFibre {
    private volatile MoralFibre realSubject;
    private final Lock initializationLock = new ReentrantLock();

    protected MoralFibre realSubject() {
        MoralFibre result = realSubject;
        if (result == null) {
            initializationLock.lock();
            try {
                result = realSubject;
                if (result == null) {
                    result = realSubject = new MoralFibreImpl();
                }
            } finally {
                initializationLock.unlock();
            }
        }
        return result;
    }
}
