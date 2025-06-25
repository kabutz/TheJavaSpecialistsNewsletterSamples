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
