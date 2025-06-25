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

class DispatchedEvent {
    private final Object mutex = new Object();
    private final Object source;
    private Component parent;
    private Cursor lastCursor;

    public DispatchedEvent(Object source) {
        this.source = source;
    }

    public void setCursor() {
        synchronized (mutex) {
            parent = findVisibleParent();
            if (parent != null) {
                lastCursor = (parent.isCursorSet() ? parent.getCursor() : null);
                parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
        }
    }

    public boolean resetCursor() {
        synchronized (mutex) {
            if (parent != null) {
                parent.setCursor(lastCursor);
                parent = null;
                return true;
            }
            return false;
        }
    }

    private Component findVisibleParent() {
        Component result = null;
        if (source instanceof Component) {
            result = SwingUtilities.getRoot((Component) source);
        } else if (source instanceof MenuComponent) {
            MenuContainer mParent = ((MenuComponent) source).getParent();
            if (mParent instanceof Component) {
                result = SwingUtilities.getRoot((Component) mParent);
            }
        }
        if ((result != null) && result.isVisible()) {
            return result;
        } else {
            return null;
        }
    }
}
