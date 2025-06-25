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
import java.awt.event.*;

public class MultiplicationTable3 extends AbstractTableFrame {
    public MultiplicationTable3() {
        super("Multiplication Table");
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
        MultiplicationTable3 frame = new MultiplicationTable3();
        frame.setSize(603, 483);
        Windows.centerOnScreen(frame);
        // instead of enabling the events and listening to window events:
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}
