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

public class SimpleTableFrame extends AbstractTableFrame {
    public SimpleTableFrame() {
        super("Simple Table");
    }

    protected TableModel makeTableModel() {
        return new DefaultTableModel(3, 4);
    }

    protected JLabel getDescription() {
        return new JLabel("Empty Default Table");
    }

    protected JButton[] makeButtons() {
        return new JButton[]{new JButton(new AbstractAction("Close") {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        })};
    }

    public static void main(String[] args) {
        SimpleTableFrame frame = new SimpleTableFrame();
        frame.setSize(603, 483);
        Windows.centerOnScreen(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}
