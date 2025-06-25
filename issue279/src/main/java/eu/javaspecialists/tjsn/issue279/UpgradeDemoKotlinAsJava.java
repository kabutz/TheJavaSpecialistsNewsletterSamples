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

package eu.javaspecialists.tjsn.issue279;

import java.util.concurrent.locks.*;

public class UpgradeDemoKotlinAsJava {
    public static void main(String... args) {
        var rwlock = new ReentrantReadWriteLock();
        System.out.println(rwlock); // w=0, r=0
        rwlock.readLock().lock();
        try {
            System.out.println(rwlock); // w=0, r=1
            int readCount = rwlock.getWriteHoldCount() == 0
                    ? rwlock.getReadHoldCount() : 0;
            for (int i = 0; i < readCount; i++)
                rwlock.readLock().unlock();
            rwlock.writeLock().lock();
            try {
                System.out.println(rwlock); // w=1, r=0
            } finally {
                for (int i = 0; i < readCount; i++)
                    rwlock.readLock().lock();
                rwlock.writeLock().unlock();
            }
            System.out.println(rwlock); // w=0, r=1
        } finally {
            rwlock.readLock().unlock();
        }
        System.out.println(rwlock); // w=0, r=0
    }
}
