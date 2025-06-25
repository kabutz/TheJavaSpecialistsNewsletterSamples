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

package eu.javaspecialists.tjsn.issue147;

public class BadClass extends Thread {
    private final Object lock1;
    private final Object lock2;

    public BadClass(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    public void run() {
        while (true) {
            synchronized (lock1) {
                synchronized (lock2) {
                    System.out.print('.');
                    System.out.flush();
                }
            }
        }
    }

    public static void main(String... args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        BadClass bc1 = new BadClass(lock1, lock2);
        BadClass bc2 = new BadClass(lock2, lock1);
        bc1.start();
        bc2.start();
    }
}
