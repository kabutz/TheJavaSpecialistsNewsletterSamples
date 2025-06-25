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
