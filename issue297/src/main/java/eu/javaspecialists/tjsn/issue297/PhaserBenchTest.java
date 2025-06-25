package eu.javaspecialists.tjsn.issue297;

import java.util.concurrent.*;
import java.util.stream.*;

public class PhaserBenchTest {
    public static void main(String... args) {
        System.out.println("Phaser");
        ForkJoinPoolBench.test(() -> {
            int upto = 4 * Runtime.getRuntime().availableProcessors();
            var phaser = new Phaser(upto);
            IntStream.range(0, upto)
                    .parallel()
                    .forEach(ignored -> phaser.arriveAndAwaitAdvance());
            System.out.println("done");
        }, (realTime, userTimeStats, cpuTimeStats, allocationStats)
                -> System.out.println(
                "realTime = " + realTime + ",\n" +
                        " userTimeStats = " + userTimeStats + ",\n" +
                        " cpuTimeStats = " + cpuTimeStats + ",\n" +
                        " allocationStats = " + allocationStats));
    }
}
