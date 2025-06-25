package eu.javaspecialists.tjsn.issue125;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
