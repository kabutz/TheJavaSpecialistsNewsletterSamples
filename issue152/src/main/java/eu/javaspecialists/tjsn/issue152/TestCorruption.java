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

package eu.javaspecialists.tjsn.issue152;

import java.util.*;
import java.util.concurrent.*;

public class TestCorruption {
    private static final int THREADS = 2;
    private static final CountDownLatch latch =
            new CountDownLatch(THREADS);
    private static final BankAccount heinz =
            new BankAccountUnsynchronized(1000);
            // new BankAccountInvariantCheck(1000);
            // new BankAccountVolatile(1000);
            // new BankAccountSynchronized(1000);
            // new BankAccountReentrantLock(1000);
            // new BankAccountReentrantReadWriteLock(1000);

    public static void main(String... args) {
        for (int i = 0; i < THREADS; i++) {
            addThread();
        }
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println(heinz.getBalance());
            }
        }, 100, 1000);
    }

    private static void addThread() {
        new Thread() {
            {start();}

            public void run() {
                latch.countDown();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    return;
                }
                while (true) {
                    heinz.deposit(100);
                    heinz.withdraw(100);
                }
            }
        };
    }
}
