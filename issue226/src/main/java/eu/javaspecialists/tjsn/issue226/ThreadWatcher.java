package eu.javaspecialists.tjsn.issue226;

import java.security.*;
import java.util.function.*;

public class ThreadWatcher extends SecurityManager {
    private final Predicate<Thread> predicate;
    private final Consumer<Thread> action;

    public ThreadWatcher(Predicate<Thread> predicate,
                         Consumer<Thread> action) {
        this.predicate = predicate;
        this.action = action;
    }

    public void checkPermission(Permission perm) {
        // allow everything
    }

    public void checkPermission(Permission perm, Object context) {
        // allow everything
    }

    public void checkAccess(ThreadGroup g) {
        Thread creatingThread = Thread.currentThread();
        if (predicate.test(creatingThread)) {
            action.accept(creatingThread);
        }
    }
}
