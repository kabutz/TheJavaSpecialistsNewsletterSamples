package eu.javaspecialists.tjsn.issue323;

import java.lang.ref.*;
import java.util.*;

public final class ThreadableLeaf implements Threadable {
    private final Reference<Thread> thread;

    public ThreadableLeaf(Thread thread) {
        this.thread = new WeakReference<>(thread);
    }

    public void accept(ThreadableVisitor visitor) {
        var thread = this.thread.get();
        if (thread != null && thread.isAlive()) {
            visitor.visit(this);
            if (thread.isVirtual()) visitor.visitVirtualThread(thread);
            else visitor.visitPlatformThread(thread);
        }
    }

    public String toString() {
        return Objects.toString(thread.get());
    }
}
