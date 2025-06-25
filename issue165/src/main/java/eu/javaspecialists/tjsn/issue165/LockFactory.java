package eu.javaspecialists.tjsn.issue165;

import java.util.concurrent.locks.*;

public interface LockFactory {
    Lock createLock(boolean readOnly);
}