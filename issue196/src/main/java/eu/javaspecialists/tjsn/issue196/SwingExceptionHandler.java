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

package eu.javaspecialists.tjsn.issue196;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.*;

public class SwingExceptionHandler implements
        Thread.UncaughtExceptionHandler {
    public void uncaughtException(final Thread t,
                                  final Throwable e) {
        if (SwingUtilities.isEventDispatchThread()) {
            showMessage(t, e);
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        showMessage(t, e);
                    }
                });
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (InvocationTargetException ite) {
                // not much more we can do here except log the exception
                ite.getCause().printStackTrace();
            }
        }
    }

    private String generateStackTrace(Throwable e) {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        pw.close();
        return writer.toString();
    }

    private void showMessage(Thread t, Throwable e) {
        String stackTrace = generateStackTrace(e);
        // show an error dialog
        JOptionPane.showMessageDialog(findActiveOrVisibleFrame(),
                stackTrace, "Exception Occurred in " + t,
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * We look for an active frame and attach ourselves to that.
     */
    private Frame findActiveOrVisibleFrame() {
        Frame[] frames = JFrame.getFrames();
        for (Frame frame : frames) {
            if (frame.isActive()) {
                return frame;
            }
        }
        for (Frame frame : frames) {
            if (frame.isVisible()) {
                return frame;
            }
        }
        return null;
    }
}
