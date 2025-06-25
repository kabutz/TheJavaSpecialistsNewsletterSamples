package eu.javaspecialists.tjsn.issue178;

public interface Processor<E> {
    boolean process(E e);
}