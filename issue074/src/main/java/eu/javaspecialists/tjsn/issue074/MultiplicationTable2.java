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

package eu.javaspecialists.tjsn.issue074;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class MultiplicationTable2 extends JFrame {
    public MultiplicationTable2(String title) {
        super(title);
        JPanel titlePanel = new JPanel();
        titlePanel.add(getDescription());
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        JTable table = new JTable(makeTableModel());
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton[] buttons = makeButtons();
        for (int i = 0; i < buttons.length; i++) {
            buttonPanel.add(buttons[i]);
        }
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    protected TableModel makeTableModel() {
        return new DefaultTableModel(100, 20) {
            public String getColumnName(int column) {
                return Integer.toString(column + 1);
            }

            public Object getValueAt(int row, int column) {
                return Integer.toString((row + 1) * (column + 1));
            }
        };
    }

    protected JLabel getDescription() {
        return new JLabel("Multiplication Table");
    }

    protected JButton[] makeButtons() {
        final JButton okButton = new JButton("OK");
        final JButton helpButton = new JButton("Help");
        okButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                okButton.setEnabled(false);
            }

            public void mouseExited(MouseEvent e) {
                okButton.setEnabled(true);
            }
        });
        helpButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                helpButton.setText("No Help");
            }
        });
        return new JButton[]{okButton, helpButton};
    }

    public static void main(String[] args) {
        MultiplicationTable2 frame = new MultiplicationTable2("Multiplication Table");
        // it is better to set the size outside of the frame construction
        frame.setSize(603, 483);
        Windows.centerOnScreen(frame);
        // instead of enabling the events and listening to window events:
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}
