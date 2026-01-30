/*
 * Copyright 2000-2026 Heinz Max Kabutz
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

package eu.javaspecialists.tjsn.issue331;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

// Use the JVM args --add-opens java.base/java.lang=ALL-UNNAMED
public class ThreadInspectorDemo {
    public static void main(String... args)
            throws InterruptedException, IOException {
        demo(Thread.ofVirtual());
        demo(Thread.ofPlatform());
    }

    private static void demo(Thread.Builder builder)
            throws InterruptedException, IOException {
        System.out.println(builder.getClass().getSimpleName());

        demoUnstarted(builder);
        demoTerminated(builder);
        demoRunning(builder);
        demoWaiting(builder);
        demoBlocked(builder);
        demoSleeping(builder);
        demoWaitingOnIO(builder);

        System.out.println();
    }

    private static void demoUnstarted(Thread.Builder builder) {
        demo("Unstarted", builder.unstarted(() -> {}));
    }

    private static void demoTerminated(Thread.Builder builder)
            throws InterruptedException {
        var terminatedThread = builder.start(() -> {});
        terminatedThread.join();
        demo("Terminated", terminatedThread);
    }

    private static void demoRunning(Thread.Builder builder)
            throws InterruptedException {
        var running = new AtomicBoolean(true);
        var runningThread = builder.start(() -> {
            while (running.get()) ;
        });
        Thread.sleep(10); // give thread a chance to really start
        demo("Running", runningThread);
        running.set(false);
        runningThread.join();
    }

    private static void demoWaiting(Thread.Builder builder)
            throws InterruptedException {
        var monitor = new Object();
        var waitingThread = builder.start(() -> {
            synchronized (monitor) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    throw new CancellationException();
                }
            }
        });
        Thread.sleep(10);
        demo("Waiting", waitingThread);
        synchronized (monitor) {
            monitor.notify();
        }
        waitingThread.join();
    }

    private static void demoBlocked(Thread.Builder builder)
            throws InterruptedException {
        var monitor = new Object();
        synchronized (monitor) {
            var blockedThread = builder.start(() -> {
                synchronized (monitor) {}
            });
            Thread.sleep(10);
            demo("Blocked", blockedThread);
        }
    }

    private static void demoSleeping(Thread.Builder builder)
            throws InterruptedException {
        var sleepingThread = builder.start(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new CancellationException();
            }
        });
        Thread.sleep(10);
        demo("Sleeping", sleepingThread);
    }

    private static void demoWaitingOnIO(Thread.Builder builder)
            throws InterruptedException, IOException {
        var waitingOnIOThread = builder.start(() -> {
            try (var serverSocket = new ServerSocket(8080)) {
                serverSocket.accept();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
        Thread.sleep(100); // needs longer wait
        demo("Waiting on IO", waitingOnIOThread);
        new Socket("localhost", 8080);
        waitingOnIOThread.join();
    }

    private static void demo(String description, Thread thread) {
        System.out.printf("%s: %s%n", description,
                ThreadInspector.getCompoundThreadStates(thread));
    }
}
