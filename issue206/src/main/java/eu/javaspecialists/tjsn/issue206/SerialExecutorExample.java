package eu.javaspecialists.tjsn.issue206;

import java.util.*;
import java.util.concurrent.*;

public class SerialExecutorExample {
    private static final int UPTO = 10;

    public static void main(String... args) {
        ExecutorService cached = Executors.newCachedThreadPool();
        test(new SerialExecutor(cached));
        test(cached);
        cached.shutdown();
    }

    private static void test(Executor executor) {
        final Vector<Integer> call_sequence = new Vector<>();
        final Phaser phaser = new Phaser(1);
        for (int i = 0; i < UPTO; i++) {
            phaser.register();
            final int tempI = i;
            executor.execute(new Runnable() {
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(
                                ThreadLocalRandom.current().nextInt(2, 10)
                        );
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    call_sequence.add(tempI);
                    phaser.arrive();
                }
            });
        }
        // we need to wait until all the jobs are done
        phaser.arriveAndAwaitAdvance();
        System.out.println(call_sequence);
    }
}
