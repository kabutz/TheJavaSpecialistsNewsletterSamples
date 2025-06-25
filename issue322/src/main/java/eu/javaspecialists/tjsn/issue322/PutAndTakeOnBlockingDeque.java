package eu.javaspecialists.tjsn.issue322;

import java.util.concurrent.*;

public class PutAndTakeOnBlockingDeque {
    public static void main(String... args) {
        PutOnBlockingQueue.test(new LinkedBlockingDeque<>());
        TakeOnBlockingQueue.test(new LinkedBlockingDeque<>());
    }
}
