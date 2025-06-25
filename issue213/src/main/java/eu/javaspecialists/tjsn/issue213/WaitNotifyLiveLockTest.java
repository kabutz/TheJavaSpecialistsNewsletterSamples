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

package eu.javaspecialists.tjsn.issue213;

import java.util.concurrent.*;

public class WaitNotifyLiveLockTest {
    public static void main(String... args) throws Exception {
        // Local variables and parameters accessed from inner classes
        // in Java 8 do not need to be explicitly declared as final!
        // They are implicitely final.
        WaitNotifyLivelock wnll = new WaitNotifyLivelock();
        ExecutorService pool = Executors.newCachedThreadPool();
        Future<?> waitForFuture = pool.submit(wnll::waitFor);
        // "wnll::waitFor" is a Java 8 method reference and is
        // roughly equivalent to:
        // pool.submit(new Runnable() {
        //   public void run() {
        //     wnll.waitFor();
        //   }
        // });
        while (WaitNotifyLivelock.waitingThread == null) {
            Thread.sleep(10);
        }
        // now we interrupt the thread waiting for the signal
        WaitNotifyLivelock.waitingThread.interrupt();

        Future<?> notifyFuture = pool.submit(wnll::notifyIt);

        try {
            notifyFuture.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.err.println("notifyFuture could not complete");
        }
        try {
            waitForFuture.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.err.println("waitForFuture could not complete");
            System.out.println("Waiting thread state: " +
                    WaitNotifyLivelock.waitingThread.getState());
        }
    }
}
