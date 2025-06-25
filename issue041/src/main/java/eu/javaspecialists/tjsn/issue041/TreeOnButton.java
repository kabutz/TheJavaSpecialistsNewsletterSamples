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
import javax.swing.event.*;
import java.awt.*;

public class TreeOnButton {
    public static void main(String[] args) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());

        final JLabel buttonText = new JLabel("Press me",
                JLabel.CENTER);
        button.add(buttonText, BorderLayout.NORTH);

        JTree tree = new JTree();
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                buttonText.setText("Press for " +
                        e.getPath().getLastPathComponent());
            }
        });
        button.add(tree, BorderLayout.CENTER);

        JFrame f = new JFrame("Tree on Button");
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(button);
        f.setSize(500, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.show();
    }
}