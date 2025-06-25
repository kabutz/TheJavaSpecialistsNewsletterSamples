package eu.javaspecialists.tjsn.issue322;

import java.util.concurrent.*;

public class PutAndTakeEarlyThrowers {
    public static void main(String... args) {
        PutOnBlockingQueue.test(EarlyInterruptedExceptionThrower.proxy(
                BlockingDeque.class, new LinkedBlockingDeque<>()));
        TakeOnBlockingQueue.test(EarlyInterruptedExceptionThrower.proxy(
                BlockingDeque.class, new LinkedBlockingDeque<>()));

        // these also work
        PutOnBlockingQueue.test(EarlyInterruptedExceptionThrower.proxy(
                BlockingQueue.class, new LinkedTransferQueue<>()));
        PutOnBlockingQueue.test(EarlyInterruptedExceptionThrower.proxy(
                BlockingQueue.class, new PriorityBlockingQueue<>()));
    }
}
