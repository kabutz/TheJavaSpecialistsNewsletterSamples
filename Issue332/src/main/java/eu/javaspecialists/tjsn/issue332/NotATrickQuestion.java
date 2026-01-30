package eu.javaspecialists.tjsn.issue332;

import java.util.stream.*;

public class NotATrickQuestion {
    void main() {
        var time = System.nanoTime();
        try {
            var runtime = Runtime.getRuntime();
            IntStream.range(0, runtime.availableProcessors())
                    .parallel()
                    .forEach(i -> {
                        System.out.println(Thread.currentThread());
                        var until = System.nanoTime() + 1_000_000_000;
                        while (System.nanoTime() <= until) ;
                    });
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}