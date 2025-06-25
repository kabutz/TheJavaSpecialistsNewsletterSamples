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

import junit.framework.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class WaitCursorEventQueuePerformanceTest
        extends TestCase {
    private static final long FAST = 5;
    private static final long SLOW = 50;
    private static final long MIXED = -1;
    private static final long TIMEOUT = 15;
    private Dialog dialog;
    private Frame frame;

    public WaitCursorEventQueuePerformanceTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        frame = new Frame();
        frame.pack();
        frame.setBounds(-1000, -1000, 100, 100);
        frame.setVisible(true);
        dialog = new Dialog(frame, true);
        dialog.pack();
        dialog.setBounds(-1000, -1000, 100, 100);
    }

    protected void tearDown() throws Exception {
        frame.dispose();
        dialog.dispose();
    }

    private long postEvents(long time) throws InterruptedException {
        InvocationEvent repeatEvent = new InvocationEvent(
                frame, new TimedEvent(time));
        InvocationEvent finalEvent = new InvocationEvent(
                frame, new TimedEvent(time), this, false);
        EventQueue q = Toolkit.getDefaultToolkit().getSystemEventQueue();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
            q.postEvent(repeatEvent);
        synchronized (this) {
            q.postEvent(finalEvent);
            wait(); //we will be notified by finalEvent when it gets posted
        }
        long endTime = System.currentTimeMillis();
        return (endTime - startTime);
    }

    public void testNormalPerformanceWithFastEvents()
            throws InterruptedException {
        System.out.println("\nnormal with fast: " + postEvents(FAST));
    }

    public void testNormalPerformanceWithSlowEvents()
            throws InterruptedException {
        System.out.println("\nnormal with slow: " + postEvents(SLOW));
    }

    public void testNormalPerformanceWithMixedEvents()
            throws InterruptedException {
        System.out.println("\nnormal with random: " + postEvents(MIXED));
    }

    public void testWaitQueuePerformanceWithFastEvents()
            throws InterruptedException {
        WaitCursorEventQueue waitQueue = new WaitCursorEventQueue(
                (int) TIMEOUT);
        Toolkit.getDefaultToolkit().getSystemEventQueue()
                .push(waitQueue);
        System.out.println("\nwait with fast: " + postEvents(FAST));
        waitQueue.close();
    }

    public void testWaitQueuePerformanceWithSlowEvents()
            throws InterruptedException {
        WaitCursorEventQueue waitQueue = new WaitCursorEventQueue(
                (int) TIMEOUT);
        Toolkit.getDefaultToolkit().getSystemEventQueue()
                .push(waitQueue);
        System.out.println("\nwait with slow: " + postEvents(SLOW));
        waitQueue.close();
    }

    public void testWaitQueuePerformanceWithMixedEvents()
            throws InterruptedException {
        WaitCursorEventQueue waitQueue = new WaitCursorEventQueue(
                (int) TIMEOUT);
        Toolkit.getDefaultToolkit().getSystemEventQueue()
                .push(waitQueue);
        System.out.println("\nwait with random: " + postEvents(MIXED));
        waitQueue.close();
    }

    public void testWaitQueuePerformanceWithDialogWithFastEvents()
            throws InterruptedException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                dialog.setVisible(true);
            }
        });
        WaitCursorEventQueue waitQueue = new WaitCursorEventQueue(
                (int) TIMEOUT);
        Toolkit.getDefaultToolkit().getSystemEventQueue()
                .push(waitQueue);
        System.out.println("\nwait with dialog with fast: " +
                postEvents(FAST));
        waitQueue.close();
    }

    public void testWaitQueuePerformanceWithDialogWithSlowEvents()
            throws InterruptedException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                dialog.setVisible(true);
            }
        });
        WaitCursorEventQueue waitQueue = new WaitCursorEventQueue(
                (int) TIMEOUT);
        Toolkit.getDefaultToolkit().getSystemEventQueue()
                .push(waitQueue);
        System.out.println("\nwait with dialog with slow: " +
                postEvents(SLOW));
        waitQueue.close();
    }

    public void testWaitQueuePerformanceWithDialogWithMixedEvents()
            throws InterruptedException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                dialog.setVisible(true);
            }
        });
        WaitCursorEventQueue waitQueue = new WaitCursorEventQueue(
                (int) TIMEOUT);
        Toolkit.getDefaultToolkit().getSystemEventQueue()
                .push(waitQueue);
        System.out.println("\nwait with dialog with random: " +
                postEvents(MIXED));
        waitQueue.close();
    }

    private class TimedEvent implements Runnable {
        private Random random;
        private long time;

        public TimedEvent(long time) {
            this.time = time;
            if (time == MIXED) {
                random = new Random(1000);
            }
        }

        public void run() {
            try {
                if (time == MIXED) {
                    Thread.sleep(
                            (long) (random.nextDouble() * (SLOW - FAST) +
                                    FAST));
                } else {
                    Thread.sleep(time);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
