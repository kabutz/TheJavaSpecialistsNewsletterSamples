package eu.javaspecialists.tjsn.issue094;

public abstract class A {
    public A(int i) {
        build(i);
    }

    protected abstract void build(int i);
}
