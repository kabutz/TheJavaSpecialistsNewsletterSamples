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

public abstract class AbstractTableFrame extends JFrame {
    public AbstractTableFrame(String title) {
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

    protected abstract TableModel makeTableModel();

    protected abstract JLabel getDescription();

    protected abstract JButton[] makeButtons();
}
