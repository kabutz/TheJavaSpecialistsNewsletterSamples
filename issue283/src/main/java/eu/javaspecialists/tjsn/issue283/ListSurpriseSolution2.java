package eu.javaspecialists.tjsn.issue283;

import java.util.*;
import java.util.concurrent.*;

public class ListSurpriseSolution2 extends ListSurprise {
    private static void doSomething(List<Integer> list) {
        // Set item 5 to 9; block main thread as we remove last item
        list.set(5, 9);
        Phaser phaser = new Phaser(2);
        Thread main = Thread.currentThread();
        new Thread(() -> {
            synchronized (System.out) {
                phaser.arriveAndDeregister();
                while (main.getState() != Thread.State.BLOCKED)
                    Thread.onSpinWait();
                list.remove(6);
            }
        }).start();
        phaser.arriveAndAwaitAdvance();
    }
}
