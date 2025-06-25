package eu.javaspecialists.tjsn.issue193;

import java.util.concurrent.locks.*;

public class ReentrantLockFactory implements ObjectFactory {
    public Object makeObject() {
        return new ReentrantLock();
    }
}