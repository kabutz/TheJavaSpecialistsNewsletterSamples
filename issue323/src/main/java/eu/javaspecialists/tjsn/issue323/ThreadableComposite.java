package eu.javaspecialists.tjsn.issue323;

import jdk.internal.vm.*;

import java.util.stream.*;

public final class ThreadableComposite implements Threadable {
    private final ThreadContainer threadContainer;

    public ThreadableComposite(ThreadContainer threadContainer) {
        this.threadContainer = threadContainer;
    }

    public Stream<Threadable> children() {
        return Stream.concat(
                threadContainer.threads().map(ThreadableLeaf::new),
                threadContainer.children().map(ThreadableComposite::new)
        );
    }

    public void accept(ThreadableVisitor visitor) {
        visitor.visit(this);
    }

    public String toString() {
        return threadContainer + " (" + threadContainer.getClass() + ")";
    }
}
