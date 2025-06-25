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

public class StampedLockUpgradeDemo {
    public static void main(String... args) {
        var sl = new StampedLock();
        System.out.println(sl); // Unlocked
        long rstamp = sl.readLock();
        System.out.println(sl); // Read-locks:1
        long wstamp = sl.tryConvertToWriteLock(rstamp);
        if (wstamp != 0) {
            // works if no one else has a read-lock
            System.out.println("Converted read to write");
            System.out.println(sl); // Write-locked
            sl.unlockWrite(wstamp);
        } else {
            // we do not have an exclusive hold on read-lock
            System.out.println("Could not convert read to write");
            sl.unlockRead(rstamp);
        }
        System.out.println(sl); // Unlocked
    }
}
