package eu.javaspecialists.tjsn.issue076;

public class A {
    // ...
    public void f() {
        assert !Thread.holdsLock(this);
        synchronized (this) {
            // ...
        }
    }
}
