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

package eu.javaspecialists.tjsn.issue030;


import javax.swing.*;
import java.awt.event.*;

public class PreferencesEditorDemo {
    public static void main(String... args) {
        JFrame frame = new JFrame("PreferencesEditorDemo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        menu.add(new JMenuItem(
                new AbstractAction("Preferences") {
                    public void actionPerformed(ActionEvent e) {
                        PreferencesEditor dialog = new PreferencesEditor(frame, "Our Preferences");
                        dialog.setLocationRelativeTo(frame);
                        dialog.setModal(true);
                        dialog.setVisible(true);
                    }
                }));
        menu.add(new JMenuItem(new AbstractAction("Exit") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }));
        frame.setSize(500, 400);
        frame.setJMenuBar(menuBar);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
