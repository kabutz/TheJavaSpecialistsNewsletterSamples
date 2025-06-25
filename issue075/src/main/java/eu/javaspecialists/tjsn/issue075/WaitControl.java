package eu.javaspecialists.tjsn.issue075;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class WaitControl {
    private static int waiting = 0;
    private static ArrayList events = new ArrayList();
    private static final Cursor DEFAULT_CURSOR =
            Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private static final Cursor WAIT_CURSOR =
            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);

    public static Window startWait(Component componentInWindow) {
        Window window = getEnclosingWindow(componentInWindow);
        if (window == null) {
            return null;
        }
        waiting++;
        // Only wait if we are not already
        if (waiting == 1) {
            window.setCursor(WAIT_CURSOR);
            EventQueue q = window.getToolkit().getSystemEventQueue();
            try {
                while (q.peekEvent() != null) {
                    events.add(q.getNextEvent());
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        return window;
    }

    public static void endWait(Component componentInWindow) {
        Window window = getEnclosingWindow(componentInWindow);
        if (window == null) {
            return;
        }
        if (waiting > 0) {
            waiting--;
            // Only stop when all waiting is done
            if (waiting == 0) {
                EventQueue q = window.getToolkit().getSystemEventQueue();
                try {
                    while (q.peekEvent() != null) {
                        AWTEvent event = q.getNextEvent();
                        if (!(event instanceof InputEvent)) {
                            events.add(event);
                        }
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                for (Iterator it = events.iterator(); it.hasNext(); ) {
                    q.postEvent((AWTEvent) it.next());
                }
                window.setCursor(DEFAULT_CURSOR);
                events.clear();
            }
        }
    }

    public static void fullEndWait(Window window) {
        if (waiting > 0) {
            waiting = 1;
            endWait(window);
        }
    }

    public static Window getEnclosingWindow(Component componentInWindow) {
        if (componentInWindow instanceof Window) {
            return (Window) componentInWindow;
        } else if (componentInWindow != null) {
            return SwingUtilities.windowForComponent(componentInWindow);
        } else {
            return null;
        }
    }
}
