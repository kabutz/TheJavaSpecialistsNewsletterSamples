package eu.javaspecialists.tjsn.issue322;

import java.util.concurrent.*;

public class PutAndTakeOnBetterBlockingDeque {
    public static void main(String... args) {
        PutOnBlockingQueue.test(new BetterLinkedBlockingDeque<>());
        TakeOnBlockingQueue.test(new BetterLinkedBlockingDeque<>());
    }
}
