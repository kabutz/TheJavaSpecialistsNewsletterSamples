package eu.javaspecialists.tjsn.issue160;

/**
 * Method1 and method2 should reliably deadlock every time they
 * get called at roughly the same time.
 */
public interface DeadlockingClass {
    void method1() throws InterruptedException;

    void method2() throws InterruptedException;
}