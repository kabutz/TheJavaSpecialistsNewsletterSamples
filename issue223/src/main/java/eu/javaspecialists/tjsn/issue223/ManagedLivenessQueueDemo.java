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

package eu.javaspecialists.tjsn.issue223;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.stream.*;

public class ManagedLivenessQueueDemo {
    private static final LinkedBlockingQueue<Integer> numbers =
            new LinkedBlockingQueue<>();

    public static void main(String... args) {
        ManagedBlockers.makeManaged(numbers);
        Thread jamThread = makeJamThread();
        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));
        for (int i = 0; i < 100; i++) {
            numbers.add(i);
        }
    }

    private static Thread makeJamThread() {
        Thread jamup = new Thread(() -> {
            int par = Runtime.getRuntime().availableProcessors() * 4;
            IntStream.range(0, par).parallel().forEach(
                    i -> {
                        System.out.println(i + ": Waiting for number " +
                                Thread.currentThread().getName());
                        int num = Interruptions.saveForLaterTask(
                                () -> numbers.take());
                        System.out.println("Got number: " + num);
                    }
            );
        });
        jamup.start();
        return jamup;
    }
}
