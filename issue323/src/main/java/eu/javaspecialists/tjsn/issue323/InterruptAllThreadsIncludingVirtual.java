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

package eu.javaspecialists.tjsn.issue323;

import java.util.concurrent.*;

// Compile and run with --add-exports java.base/jdk.internal.vm=ALL-UNNAMED
public class InterruptAllThreadsIncludingVirtual {
    public static void main(String... args) throws Exception {
        var interrupter = new ThreadableVisitor() {
            public void visit(Thread thread) {
                thread.interrupt();
            }
        };

        var sleepingVirtualThread = Thread.ofVirtual()
                .name("Sleeping Virtual Thread").start(() -> {
                    try {
                        Thread.sleep(10_000);
                    } catch (InterruptedException e) {
                        System.out.println("Virtual thread sleep interrupted");
                    }
                });
        try (var fixedPool = Executors.newFixedThreadPool(2);
             var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            fixedPool.submit(() -> {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    System.out.println("Fixed thread sleep interrupted");
                }
                return "done";
            });
            scope.fork(() -> {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    System.out.println("Forked sleep interrupted");
                }
                return "done";
            });
            scope.fork(() -> {
                try (var inner = new StructuredTaskScope.ShutdownOnFailure()) {
                    inner.fork(() -> {
                        try {
                            Thread.sleep(10_000);
                        } catch (InterruptedException e) {
                            System.out.println("Inner forked sleep interrupted");
                        }
                        return "done";
                    });
                    try {
                        inner.join().throwIfFailed();
                    } catch (InterruptedException e) {
                        System.out.println("Inner scope interrupted");
                    }
                    return "done";
                }
            });
            Thread.sleep(100);
            Threadables.create().accept(interrupter);
            System.out.println("All threads interrupted");

            Thread.interrupted(); // clear my interrupt
            scope.join().throwIfFailed();
            sleepingVirtualThread.join();
        }
    }
}
