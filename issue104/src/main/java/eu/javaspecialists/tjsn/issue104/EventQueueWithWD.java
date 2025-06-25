package eu.javaspecialists.tjsn.issue104;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Alternative events dispatching queue. The benefit over the
 * default Event Dispatch queue is that you can add as many
 * watchdog timers as you need and they will trigger arbitrary
 * actions when processing of single event will take longer than
 * one timer period.
 * <p/>
 * Timers can be of two types:
 * <ul>
 * <li><b>Repetitive</b> - action can be triggered multiple times
 * for the same "lengthy" event dispatching.
 * </li>
 * <li><b>Non-repetitive</b> - action can be triggered only once
 * per event dispatching.</li>
 * </ul>
 * <p/>
 * The queue records time of the event dispatching start.  This
 * time is used by the timers to check if dispatching takes
 * longer than their periods. If so the timers trigger associated
 * actions.
 * <p/>
 * In order to use this queue application should call
 * <code>install()</code> method. This method will create,
 * initialize and register the alternative queue as appropriate.
 * It also will return the instance of the queue for further
 * interactions. Here's an example of how it can be done:
 * <p/>
 * <pre>
 * <p/>
 *  EventQueueWithWD queue = EventQueueWithWD.install();
 *  Action edtOverloadReport = ...;
 * <p/>
 *  // install single-shot wg to report EDT overload after
 *  // 10-seconds timeout
 *  queue.addWatchdog(10000, edtOverloadReport, false);
 * <p/>
 * </pre>
 */
public class EventQueueWithWD extends EventQueue {
  // Main timer
  private final java.util.Timer timer = new java.util.Timer(true);

  // Group of informational fields for describing the event
private final Object eventChangeLock = new Object();
  private volatile long eventDispatchingStart = -1;
  private volatile AWTEvent event = null;

  /**
   * Hidden utility constructor.
   */
private EventQueueWithWD() { }

  /**
   * Install alternative queue.
   *
   * @return instance of queue installed.
   */
public static EventQueueWithWD install() {
    EventQueue eventQueue =
        Toolkit.getDefaultToolkit().getSystemEventQueue();
    EventQueueWithWD newEventQueue = new EventQueueWithWD();
    eventQueue.push(newEventQueue);
    return newEventQueue;
  }

  /**
   * Record the event and continue with usual dispatching.
   *
   * @param anEvent event to dispatch.
   */
protected void dispatchEvent(AWTEvent anEvent) {
    setEventDispatchingStart(anEvent, System.currentTimeMillis());
    super.dispatchEvent(anEvent);
    setEventDispatchingStart(null, -1);
  }

  /**
   * Register event and dispatching start time.
   *
   * @param anEvent   event.
   * @param timestamp dispatching start time.
   */
private void setEventDispatchingStart(AWTEvent anEvent,
                                        long timestamp) {
    synchronized (eventChangeLock) {
      event = anEvent;
      eventDispatchingStart = timestamp;
    }
  }

  /**
   * Add watchdog timer. Timer will trigger <code>listener</code>
   * if the queue dispatching event longer than specified
   * <code>maxProcessingTime</code>. If the timer is
   * <code>repetitive</code> then it will trigger additional
   * events if the processing 2x, 3x and further longer than
   * <code>maxProcessingTime</code>.
   *
   * @param maxProcessingTime maximum processing time.
   * @param listener          listener for events. The listener
   *                          will receive <code>AWTEvent</code>
   *                          as source of event.
   * @param repetitive        TRUE to trigger consequent events
   *                          for 2x, 3x and further periods.
   */
public void addWatchdog(long maxProcessingTime,
                          ActionListener listener,
                          boolean repetitive) {
    Watchdog checker = new Watchdog(maxProcessingTime, listener,
        repetitive);
    timer.schedule(checker, maxProcessingTime,
        maxProcessingTime);
  }

  /**
   * Checks if the processing of the event is longer than the
   * specified <code>maxProcessingTime</code>. If so then
   * listener is notified.
   */
private class Watchdog extends TimerTask {
    // Settings
private final long maxProcessingTime;
    private final ActionListener listener;
    private final boolean repetitive;

    // Event reported as "lengthy" for the last time. Used to
    // prevent repetitive behaviour in non-repeatitive timers.
private AWTEvent lastReportedEvent = null;

    /**
     * Creates timer.
     *
     * @param maxProcessingTime maximum event processing time
     *                           before listener is notified.
     * @param listener          listener to notify.
     * @param repetitive       TRUE to allow consequent
     *                           notifications for the same event
     */
private Watchdog(long maxProcessingTime,
                    ActionListener listener,
                    boolean repetitive) {
      if (listener == null)
        throw new IllegalArgumentException(
            "Listener cannot be null.");
      if (maxProcessingTime < 0)
        throw new IllegalArgumentException(
          "Max locking period should be greater than zero");
      this.maxProcessingTime = maxProcessingTime;
      this.listener = listener;
      this.repetitive = repetitive;
    }

    public void run() {
      long time;
      AWTEvent currentEvent;

      // Get current event requisites
synchronized (eventChangeLock) {
        time = eventDispatchingStart;
        currentEvent = event;
      }

      long currentTime = System.currentTimeMillis();

      // Check if event is being processed longer than allowed
if (time != -1 && (currentTime - time > maxProcessingTime) &&
          (repetitive || currentEvent != lastReportedEvent)) {
        listener.actionPerformed(
            new ActionEvent(currentEvent, -1, null));
        lastReportedEvent = currentEvent;
      }
    }
  }
}
