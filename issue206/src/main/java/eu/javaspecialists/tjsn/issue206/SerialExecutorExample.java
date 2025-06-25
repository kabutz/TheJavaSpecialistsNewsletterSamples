/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
