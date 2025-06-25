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

package eu.javaspecialists.tjsn.issue065;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Basic CursorToolkit that still allows mouseclicks
 */
public class CursorToolkitOne implements Cursors {
    private CursorToolkitOne() {
    }

    /**
     * Sets cursor for specified component to Wait cursor
     */
    public static void startWaitCursor(JComponent component) {
        RootPaneContainer root =
                (RootPaneContainer) component.getTopLevelAncestor();
        root.getGlassPane().setCursor(WAIT_CURSOR);
        root.getGlassPane().setVisible(true);
    }

    /**
     * Sets cursor for specified component to normal cursor
     */
    public static void stopWaitCursor(JComponent component) {
        RootPaneContainer root =
                (RootPaneContainer) component.getTopLevelAncestor();
        root.getGlassPane().setCursor(DEFAULT_CURSOR);
        root.getGlassPane().setVisible(false);
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Test App");
        frame.getContentPane().add(
                new JLabel("I'm a Frame"), BorderLayout.NORTH);
        frame.getContentPane().add(
                new JButton(new AbstractAction("Wait Cursor") {
                    public void actionPerformed(ActionEvent event) {
                        System.out.println("Setting Wait cursor on frame");
                        startWaitCursor(frame.getRootPane());
                    }
                }));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}
