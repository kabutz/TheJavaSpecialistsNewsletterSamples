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

package eu.javaspecialists.tjsn.issue081;

import javax.swing.*;
import java.awt.*;

public class ExceptionGroup extends ThreadGroup {
    public ExceptionGroup() {
        super("ExceptionGroup");
    }

    public void uncaughtException(Thread t, Throwable e) {
        JOptionPane.showMessageDialog(findActiveFrame(),
                e.toString(), "Exception Occurred", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    /**
     * I hate ownerless dialogs.  With this method, we can find the
     * currently visible frame and attach the dialog to that, instead
     * of always attaching it to null.
     */
    private Frame findActiveFrame() {
        Frame[] frames = JFrame.getFrames();
        for (int i = 0; i < frames.length; i++) {
            Frame frame = frames[i];
            if (frame.isVisible()) {
                return frame;
            }
        }
        return null;
    }
}