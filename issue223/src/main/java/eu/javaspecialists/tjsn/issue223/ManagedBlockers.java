package eu.javaspecialists.tjsn.issue223;

import java.lang.reflect.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class ManagedBlockers {
  public static <E> ArrayBlockingQueue<E> makeManaged(
      ArrayBlockingQueue<E> queue) {
    Class<?> clazz = ArrayBlockingQueue.class;

    try {
      Field lockField = clazz.getDeclaredField("lock");
      lockField.setAccessible(true);
      ReentrantLock old = (ReentrantLock) lockField.get(queue);
      boolean fair = old.isFair();
      ReentrantLock lock = new ManagedReentrantLock(fair);
      lockField.set(queue, lock);

      replace(queue, clazz, "notEmpty", lock.newCondition());
      replace(queue, clazz, "notFull", lock.newCondition());

      return queue;
    } catch (IllegalAccessException | NoSuchFieldException e) {
      throw new IllegalStateException(e);
    }
  }

  public static <E> LinkedBlockingQueue<E> makeManaged(
      LinkedBlockingQueue<E> queue) {
    Class<?> clazz = LinkedBlockingQueue.class;

    ReentrantLock takeLock = new ManagedReentrantLock();
    ReentrantLock putLock = new ManagedReentrantLock();

    try {
      replace(queue, clazz, "takeLock", takeLock);
      replace(queue, clazz, "notEmpty", takeLock.newCondition());
      replace(queue, clazz, "putLock", putLock);
      replace(queue, clazz, "notFull", putLock.newCondition());

      return queue;
    } catch (IllegalAccessException | NoSuchFieldException e) {
      throw new IllegalStateException(e);
    }
  }

  public static <E> PriorityBlockingQueue<E> makeManaged(
      PriorityBlockingQueue<E> queue) {
    Class<?> clazz = PriorityBlockingQueue.class;

    ReentrantLock lock = new ManagedReentrantLock();

    try {
      replace(queue, clazz, "lock", lock);
      replace(queue, clazz, "notEmpty", lock.newCondition());

      return queue;
    } catch (IllegalAccessException | NoSuchFieldException e) {
      throw new IllegalStateException(e);
    }
  }

  private static void replace(Object owner,
                              Class<?> clazz, String fieldName,
                              Object fieldValue)
      throws NoSuchFieldException, IllegalAccessException {
    Field field = clazz.getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(owner, fieldValue);
  }
}
