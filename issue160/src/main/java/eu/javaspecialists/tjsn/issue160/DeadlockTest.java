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

package eu.javaspecialists.tjsn.issue160;

import java.lang.management.*;
import java.util.*;
import java.util.concurrent.*;

public class DeadlockTest {
    private static final DeadlockChecker checker =
            new DeadlockChecker();

    public static void main(String... args) throws Exception {
        DeadlockingClass[] problemClasses = {
                new DeadlockedOnLocks(),
                new DeadlockedOnLocksInterruptibly(),
                new DeadlockedOnMonitorThenLock(),
                new DeadlockedOnLockThenMonitor(),
                new DeadlockedOnMonitors(),
        };
        DeadlockTest test = new DeadlockTest();
        for (DeadlockingClass problemClass : problemClasses) {
            String name = problemClass.getClass().getSimpleName();
            System.out.println(name);
            System.out.println(name.replaceAll(".", "="));
            test.resolveByStop(problemClass);
            System.out.println();
            test.resolveByInterrupt(problemClass);
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }

    private void resolveByStop(DeadlockingClass pc) {
        System.out.println("Testing deadlock resolve by stop()");
        System.out.println("----------------------------------");
        Thread[] threads = setupThreads(pc, "t1", "t2");
        sleepQuietly(2);
        check();
        System.out.println("Trying to resolve through t1.stop()");
        threads[0].stop();
        sleepQuietly(1);
        check();
        System.out.println("Trying to resolve through t2.stop()");
        threads[1].stop();
        sleepQuietly(1);
        check();
        checker.ignoreThreads(threads);
    }

    private void resolveByInterrupt(DeadlockingClass pc) throws InterruptedException {
        System.out.println("Testing deadlock resolve by interrupt()");
        System.out.println("---------------------------------------");
        Thread[] threads = setupThreads(pc, "t3", "t4");
        sleepQuietly(2);
        check();
        System.out.println("Trying to resolve by t3.interrupt()");
        threads[0].interrupt();
        sleepQuietly(1);
        check();
        System.out.println("Trying to resolve by t4.interrupt()");
        threads[1].interrupt();
        sleepQuietly(1);
        check();
        checker.ignoreThreads(threads);
    }

    private static void sleepQuietly(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static Thread[] setupThreads(
            final DeadlockingClass problemClass,
            final String name1, final String name2) {
        Thread[] threads = {
                new Thread(name1) {
                    public void run() {
                        try {
                            problemClass.method1();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.out.println(name1 + " interrupted");
                        } finally {
                            System.out.println(
                                    "******* " + name1 + " Deadlock resolved");
                        }
                    }
                },
                new Thread(name2) {
                    public void run() {
                        try {
                            problemClass.method2();
                        } catch (InterruptedException e) {
                            System.out.println(name2 + " interrupted");
                            Thread.currentThread().interrupt();
                        } finally {
                            System.out.println(
                                    "******* " + name2 + " Deadlock resolved");
                        }
                    }
                }};
        for (Thread thread : threads) {
            thread.setDaemon(true);
            thread.start();
            System.out.println("Started thread " + thread.getName() +
                    " (" + thread.getId() + ")");
        }
        return threads;
    }

    private static void check() {
        printInfo(checker.check());
    }

    private static void printInfo(
            Collection<ThreadInfo> threads) {
        if (threads.isEmpty()) {
            System.out.println("+++ No deadlock (detected)");
        } else {
            System.out.print("--- We have detected a deadlock: ");
            for (ThreadInfo info : threads) {
                System.out.print(info.getThreadName() +
                        " (id=" + info.getThreadId() + ")  ");
            }
            System.out.println();
        }
    }
}