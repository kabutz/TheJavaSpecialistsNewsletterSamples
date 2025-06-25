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

public class StopIsBad {
    private static final boolean USE_INTERRUPT = false;
    public static final Object lock = new Object();
    public static int x = 5;
    public static int y = 5;

    // invariant: x + y = 10
    static class Foo extends Thread {
        public void run() {
            while (!isInterrupted()) {
                synchronized (lock) {
                    x++;
                    sleepQuietly(); // make sure stop() happens here
                    y--;
                }
            }
        }

        private void sleepQuietly() {
            try {
                sleep(10);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String... args)
            throws InterruptedException {
        while (true) {
            System.out.println("Creating new foo");
            Foo foo = new Foo();
            foo.start();
            Thread.sleep(50);
            if (USE_INTERRUPT) {
                foo.interrupt();
            } else {
                foo.stop();
            }
            synchronized (lock) {
                if (x + y != 10) {
                    throw new IllegalStateException(
                            "Invariant is broken: " + (x + y));
                }
            }
            foo.join();
        }
    }
}
