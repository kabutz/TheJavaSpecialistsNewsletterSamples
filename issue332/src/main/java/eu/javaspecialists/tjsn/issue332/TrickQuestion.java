package eu.javaspecialists.tjsn.issue332;

import java.util.concurrent.*;

public class TrickQuestion {
    void main() {
        var time = System.nanoTime();
        try {
            ForkJoinPool.commonPool().submit(() -> {
                var until = System.nanoTime() + 1_000_000_000;
                while (System.nanoTime() <= until) ;
            }).join();
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}
