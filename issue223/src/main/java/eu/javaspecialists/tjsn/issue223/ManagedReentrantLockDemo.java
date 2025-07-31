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

import java.util.concurrent.locks.*;
import java.util.stream.*;

public class ManagedReentrantLockDemo {
    public static void main(String... args) {
        ReentrantLock lock = new ReentrantLock();
//    ReentrantLock lock = new ManagedReentrantLock();
        Condition condition = lock.newCondition();
        int upto = Runtime.getRuntime().availableProcessors() * 10;
        IntStream.range(0, upto).parallel().forEach(
                i -> {
                    lock.lock();
                    try {
                        System.out.println(i + ": Got lock, now waiting - " +
                                Thread.currentThread().getName());
                        condition.awaitUninterruptibly();
                    } finally {
                        lock.unlock();
                    }
                }
        );
    }
}
