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

package eu.javaspecialists.tjsn.issue010;

import javax.swing.*;
import java.awt.*;

public class WildLayoutExample extends JFrame {
    public WildLayoutExample() {
        super("WildLayoutExample");
        setSize(new Dimension(400, 300));
        getContentPane().setLayout(new WildLayoutManager());
        // construct the left panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JLabel("Left Label"), BorderLayout.NORTH);
        leftPanel.add(new JTree(), BorderLayout.CENTER);
        // construct the middle panel
        JPanel middlePanel = new JPanel(new GridLayout(0, 1, 5, 5));
        middlePanel.add(new JButton("Add >"), null);
        middlePanel.add(new JButton("<< Remove All"), null);
        // construct the right panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel("Right Label"), BorderLayout.NORTH);
        rightPanel.add(new JTextArea("jTextArea1"), BorderLayout.CENTER);
        // add the panels to the content pane using our new layout manager
        getContentPane().add(leftPanel, WildLayoutManager.LEFT);
        getContentPane().add(middlePanel, WildLayoutManager.MIDDLE);
        getContentPane().add(rightPanel, WildLayoutManager.RIGHT);
    }

    public static void main(String[] args) {
        WildLayoutExample frame = new WildLayoutExample();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JDK 1.3 !
        frame.setVisible(true);
    }
}