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

package eu.javaspecialists.tjsn.issue041;

import javax.swing.*;
import java.awt.*;

public class MultilineButton {
    public static void main(String[] args) {
        JButton button = new JButton();
        // It is important to set the layout manager of the button
        button.setLayout(new GridLayout(0, 1));
        // Then we simply add components to it!
        button.add(new JLabel("This is a", JLabel.CENTER));
        button.add(new JLabel("multiline", JLabel.CENTER));
        button.add(new JLabel("button.", JLabel.CENTER));
        JFrame f = new JFrame("Multi-Lined Button");
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(button);
        f.setSize(100, 100);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.show();
    }
}