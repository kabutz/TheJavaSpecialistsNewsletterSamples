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

package eu.javaspecialists.tjsn.issue104;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

/**
 * Sample usage of <code>EventQueueWithWD</code> class.
 */
public class SampleEQUsage extends JFrame {
  public SampleEQUsage() {
    super("Sample EQ Usage");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    getContentPane().add(new JButton(new AbstractAction("Go") {
      public void actionPerformed(ActionEvent e) {
        System.out.println();
        System.out.println(new Date());
        try {
          // Sleep for 10 seconds
          Thread.sleep(10000);
        } catch (InterruptedException e1) {
        }
      }
    }));

    setSize(100, 100);
  }

  public static void main(String[] args) {
    initQueue();

    SampleEQUsage sequ = new SampleEQUsage();
    sequ.setVisible(true);
  }

  // Install and init the alternative queue
private static void initQueue() {
    EventQueueWithWD queue = EventQueueWithWD.install();

    // Install 3-seconds single-shot watchdog timer
    queue.addWatchdog(3000, new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        System.out.println(new Date() + " 3 seconds - single-shot");
      }
    }, false);

    // Install 3-seconds multi-shot watchdog timer
    queue.addWatchdog(3000, new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        System.out.println(new Date() + " 3 seconds - multi-shot");
      }
    }, true);

    // Install 11-seconds multi-shot watchdog timer
    queue.addWatchdog(11000, new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        System.out.println(new Date() + " 11 seconds - multi-shot");
      }
    }, true);
  }
}
