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

package eu.javaspecialists.tjsn.issue007;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GlobalHotkeyManagerTest extends JFrame {
    private static final String UIROBOT_KEY = "UIRobot";
    private final KeyStroke uirobotHotkey = KeyStroke.getKeyStroke(
            KeyEvent.VK_R, KeyEvent.CTRL_MASK + KeyEvent.ALT_MASK, false);
    private final Action uirobot = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            setEnabled(false); // stop any other events from interfering
            JOptionPane.showMessageDialog(GlobalHotkeyManagerTest.this,
                    "UIRobot Hotkey was pressed");
            setEnabled(true);
        }
    };

    public GlobalHotkeyManagerTest() {
        super("Global Hotkey Manager Test");
        setSize(500, 400);
        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(new JButton("Button 1"));
        getContentPane().add(new JTextField(20));
        getContentPane().add(new JButton("Button 2"));
        GlobalHotkeyManager hotkeyManager = GlobalHotkeyManager.getInstance();
        hotkeyManager.getInputMap().put(uirobotHotkey, UIROBOT_KEY);
        hotkeyManager.getActionMap().put(UIROBOT_KEY, uirobot);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JDK 1.3
        setVisible(true);
    }

    public static void main(String[] args) {
        new GlobalHotkeyManagerTest();
    }
}