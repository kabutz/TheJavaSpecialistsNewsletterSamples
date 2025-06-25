package eu.javaspecialists.tjsn.issue323;

public sealed interface Threadable
        permits ThreadableComposite, ThreadableLeaf {
    void accept(ThreadableVisitor visitor);
}
