package eu.javaspecialists.tjsn.issue302;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockOwnerReveal extends ReentrantLock {
  @Override
  public Thread getOwner() {
    return super.getOwner();
  }

  @Override
  public String toString() {
    var owner = getOwner();
    return super.toString() + ((owner == null) ? "" :
        " tid=" + owner.threadId());
  }
}
