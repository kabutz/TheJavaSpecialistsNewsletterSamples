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

package eu.javaspecialists.tjsn.issue118;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.sql.*;

public class DatabaseShower extends JFrame {
    private final JList names;
    private final JTable data = new JTable();

    public DatabaseShower(final Connection con, String title)
            throws SQLException {
        super(title);
        names = new JList(new TableNameListModel(con));
        names.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Object tableName = names.getSelectedValue();
                    if (tableName != null) {
                        try {
                            data.setModel(new DatabaseTableModel(con, tableName));
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            data.setModel(new DefaultTableModel());
                        }
                    }
                }
            }
        });
        getContentPane().add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(names), new JScrollPane(data)));
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            System.err.println("Usage: java DatabaseShower " +
                    "driver url user password");
            System.exit(1);
        }
        Class.forName(args[0]);
        Connection con = DriverManager.getConnection(args[1], args[2],
                args[3]);
        String title = "Database Shower  ->  " + args[1];
        DatabaseShower frame = new DatabaseShower(con, title);
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
