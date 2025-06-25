package eu.javaspecialists.tjsn.issue249;

public class Cell {
    // @sun.misc.Contended // up to Java 8
    @jdk.internal.vm.annotation.Contended
    private volatile long value;
}
