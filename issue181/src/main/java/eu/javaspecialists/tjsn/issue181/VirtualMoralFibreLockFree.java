package eu.javaspecialists.tjsn.issue181;

import java.util.concurrent.atomic.*;

public class VirtualMoralFibreLockFree extends VirtualMoralFibre {
    private final AtomicReference<MoralFibre> realSubject =
            new AtomicReference<MoralFibre>();

    protected MoralFibre realSubject() {
        MoralFibre subject = realSubject.get();
        if (subject == null) {
            subject = new MoralFibreImpl();
            if (!realSubject.compareAndSet(null, subject)) {
                subject = realSubject.get();
            }
        }
        return subject;
    }
}
