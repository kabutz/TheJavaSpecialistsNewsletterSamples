package eu.javaspecialists.tjsn.issue332;

import java.util.concurrent.*;

public class GlobalTimer {
    static void main() throws InterruptedException {
        ForkJoinPool.commonPool().scheduleAtFixedRate(
                () -> System.out.println("Cool cat!"),
                1, 1, TimeUnit.SECONDS);
        Thread.sleep(5500);
    }
}
