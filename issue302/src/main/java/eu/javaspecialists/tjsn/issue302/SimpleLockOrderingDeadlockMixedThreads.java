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

package eu.javaspecialists.tjsn.issue302;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Phaser;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleLockOrderingDeadlockMixedThreads {
  public static void main(String... args)
      throws InterruptedException {
    var monitor1 = new Object();
    var monitor2 = new Object();
    var coop = new Phaser(2);
    Thread.ofPlatform().name("platform").start(() -> {
      synchronized (monitor1) {
        coop.arriveAndAwaitAdvance();
        synchronized (monitor2) {
          System.out.println("All's well");
        }
      }
    });
    Thread.ofVirtual().name("virtual").start(() -> {
      synchronized (monitor2) {
        coop.arriveAndAwaitAdvance();
        synchronized (monitor1) {
          System.out.println("All's well too");
        }
      }
    });
    Thread.sleep(100);
    ThreadMXBean tmb = ManagementFactory.getThreadMXBean();
    long[] deadlocks = tmb.findDeadlockedThreads();
    System.out.println("deadlocks = " + deadlocks);
  }
}
