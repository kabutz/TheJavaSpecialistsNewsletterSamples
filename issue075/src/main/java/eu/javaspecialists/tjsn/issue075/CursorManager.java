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

import java.awt.*;
import java.awt.event.*;
import java.util.*;

class CursorManager {
    private final DelayTimer waitTimer;
    private final Stack dispatchedEvents;
    private boolean needsCleanup;

    public CursorManager(DelayTimer waitTimer) {
        this.dispatchedEvents = new Stack();
        this.waitTimer = waitTimer;
    }

    private void cleanUp() {
        if (((DispatchedEvent) dispatchedEvents.peek()).resetCursor()) {
            clearQueueOfInputEvents();
        }
    }

    private void clearQueueOfInputEvents() {
        EventQueue q = Toolkit.getDefaultToolkit().getSystemEventQueue();
        synchronized (q) {
            ArrayList nonInputEvents = gatherNonInputEvents(q);
            for (Iterator it = nonInputEvents.iterator(); it.hasNext(); )
                q.postEvent((AWTEvent) it.next());
        }
    }

    private ArrayList gatherNonInputEvents(EventQueue systemQueue) {
        ArrayList events = new ArrayList();
        while (systemQueue.peekEvent() != null) {
            try {
                AWTEvent nextEvent = systemQueue.getNextEvent();
                if (!(nextEvent instanceof InputEvent)) {
                    events.add(nextEvent);
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        return events;
    }

    public void push(Object source) {
        if (needsCleanup) {
            waitTimer.stopTimer();
            cleanUp(); //this corrects the state when a modal dialog
            //opened last time round
        }
        dispatchedEvents.push(new DispatchedEvent(source));
        needsCleanup = true;
    }

    public void pop() {
        cleanUp();
        dispatchedEvents.pop();
        if (!dispatchedEvents.isEmpty()) {
            //this will be stopped if getNextEvent() is called -
            //used to watch for modal dialogs closing
            waitTimer.startTimer();
        } else {
            needsCleanup = false;
        }
    }

    public void setCursor() {
        ((DispatchedEvent) dispatchedEvents.peek()).setCursor();
    }
}
