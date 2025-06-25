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

/**
 * Suggested serving size:
 * Toolkit.getDefaultToolkit().getSystemEventQueue().push(new WaitCursorEventQueue(70));
 */
public class WaitCursorEventQueue extends EventQueue
        implements DelayTimerCallback {
    private final CursorManager cursorManager;
    private final DelayTimer waitTimer;

    public WaitCursorEventQueue(int delay) {
        this.waitTimer = new DelayTimer(this, delay);
        this.cursorManager = new CursorManager(waitTimer);
    }

    public void close() {
        waitTimer.quit();
        pop();
    }

    protected void dispatchEvent(AWTEvent event) {
        cursorManager.push(event.getSource());
        waitTimer.startTimer();
        try {
            super.dispatchEvent(event);
        } finally {
            waitTimer.stopTimer();
            cursorManager.pop();
        }
    }

    public AWTEvent getNextEvent() throws InterruptedException {
        waitTimer.stopTimer(); //started by pop(), this catches modal dialogs
//closing that do work afterwards
        return super.getNextEvent();
    }

    public void trigger() {
        cursorManager.setCursor();
    }
}
