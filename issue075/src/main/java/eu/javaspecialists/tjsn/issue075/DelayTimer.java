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

package eu.javaspecialists.tjsn.issue075;

/**
 * This class implements a delay timer that will call trigger()
 * on the DelayTimerCallback delay milliseconds after
 * startTimer() was called, if stopTimer() was not called first.
 * The timer will only throw events after startTimer() is called.
 * Until then, it does nothing.  It is safe to call stopTimer()
 * and startTimer() repeatedly.
 * <p>
 * Note that calls to trigger() will happen on the timer thread.
 * <p>
 * This class is multiple-thread safe.
 */
public class DelayTimer extends Thread {
    private final DelayTimerCallback callback;
    private final Object mutex = new Object();
    private final Object triggeredMutex = new Object();
    private final long delay;
    private boolean quit;
    private boolean triggered;
    private long waitTime;

    public DelayTimer(DelayTimerCallback callback, long delay) {
        this.callback = callback;
        this.delay = delay;
        setDaemon(true);
        start();
    }

    /**
     * Calling this method twice will reset the timer.
     */
    public void startTimer() {
        synchronized (mutex) {
            waitTime = delay;
            mutex.notify();
        }
    }

    public void stopTimer() {
        try {
            synchronized (mutex) {
                synchronized (triggeredMutex) {
                    if (triggered) {
                        triggeredMutex.wait();
                    }
                }
                waitTime = 0;
                mutex.notify();
            }
        } catch (InterruptedException ie) {
            System.err.println("trigger failure");
            ie.printStackTrace(System.err);
        }
    }

    public void run() {
        try {
            while (!quit) {
                synchronized (mutex) {
                    //we rely on wait(0) being implemented to wait forever here
                    if (waitTime < 0) {
                        triggered = true;
                        waitTime = 0;
                    } else {
                        long saveWaitTime = waitTime;
                        waitTime = -1;
                        mutex.wait(saveWaitTime);
                    }
                }
                try {
                    if (triggered) {
                        callback.trigger();
                    }
                } catch (Exception e) {
                    System.err.println(
                            "trigger() threw exception, continuing");
                    e.printStackTrace(System.err);
                } finally {
                    synchronized (triggeredMutex) {
                        triggered = false;
                        triggeredMutex.notify();
                    }
                }
            }
        } catch (InterruptedException ie) {
            System.err.println("interrupted in run");
            ie.printStackTrace(System.err);
        }
    }

    public void quit() {
        synchronized (mutex) {
            this.quit = true;
            mutex.notify();
        }
    }
}
