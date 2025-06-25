package eu.javaspecialists.tjsn.issue223;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.stream.*;

public class ManagedLivenessQueueDemo {
  private static final LinkedBlockingQueue<Integer> numbers =
      new LinkedBlockingQueue<>();

  public static void main(String... args) {
    ManagedBlockers.makeManaged(numbers);
    Thread jamThread = makeJamThread();
    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));
    for (int i = 0; i < 100; i++) {
      numbers.add(i);
    }
  }

  private static Thread makeJamThread() {
    Thread jamup = new Thread(() -> {
      int par = Runtime.getRuntime().availableProcessors() * 4;
      IntStream.range(0, par).parallel().forEach(
          i -> {
            System.out.println(i + ": Waiting for number " +
                Thread.currentThread().getName());
            int num = Interruptions.saveForLaterTask(
                () -> numbers.take());
            System.out.println("Got number: " + num);
          }
      );
    });
    jamup.start();
    return jamup;
  }
}
