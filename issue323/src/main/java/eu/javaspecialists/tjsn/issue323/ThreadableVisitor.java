package eu.javaspecialists.tjsn.issue323;

public interface ThreadableVisitor {
    default void visit(ThreadableComposite composite) {
        composite.children().forEach(
                threadable -> threadable.accept(this));
    }

    default void visit(ThreadableLeaf leaf) {}

    default void visit(Thread thread) {}

    default void visitVirtualThread(Thread thread) {
        visit(thread);
    }

    default void visitPlatformThread(Thread thread) {
        visit(thread);
    }
}
