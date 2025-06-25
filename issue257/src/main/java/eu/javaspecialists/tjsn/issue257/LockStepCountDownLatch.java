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

package eu.javaspecialists.tjsn.issue257;

import java.util.concurrent.*;

import static java.util.concurrent.Executors.*;

public class LockStepCountDownLatch extends LockStepExample {
    public static void main(String... args) {
        LockStepCountDownLatch lse = new LockStepCountDownLatch();
        ExecutorService pool = newFixedThreadPool(TASKS_PER_BATCH);
        for (int batch = 0; batch < BATCHES; batch++) {
            // We need a new CountDownLatch per batch, since they
            // cannot be reset to their initial value
            CountDownLatch latch = new CountDownLatch(TASKS_PER_BATCH);
            for (int i = 0; i < TASKS_PER_BATCH; i++) {
                int batchNumber = batch + 1;
                pool.submit(() -> lse.task(latch, batchNumber));
            }
        }
        pool.shutdown();
    }

    public void task(CountDownLatch latch, int batch) {
        latch.countDown();
        boolean interrupted = Thread.interrupted();
        while (true) {
            try {
                latch.await();
                break;
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
        if (interrupted) Thread.currentThread().interrupt();
        doTask(batch);
    }
}
