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

public class Frame1 extends JFrame {
    JPanel contentPane;
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JPanel jPanel2 = new JPanel();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JTable jTable1 = new JTable(new DefaultTableModel(100, 20) {
        public String getColumnName(int column) {
            return Integer.toString(column + 1);
        }

        public Object getValueAt(int row, int column) {
            return Integer.toString((row + 1) * (column + 1));
        }
    });

    //Construct the frame
    public Frame1() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() throws Exception {
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);
        this.setSize(new Dimension(603, 483));
        this.setTitle("Frame Title");
        jLabel1.setText("Multiplication Table");
        jButton1.setText("OK");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                jButton1_mouseEntered(e);
            }

            public void mouseExited(MouseEvent e) {
                jButton1_mouseExited(e);
            }
        });
        jButton2.setText("Help");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                jButton2_mouseClicked(e);
            }
        });
        contentPane.add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(jLabel1, null);
        contentPane.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(jTable1, null);
        contentPane.add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(jButton1, null);
        jPanel2.add(jButton2, null);
    }

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    void jButton1_mouseEntered(MouseEvent e) {
        jButton1.setEnabled(false);
    }

    void jButton1_mouseExited(MouseEvent e) {
        jButton1.setEnabled(true);
    }

    void jButton2_mouseClicked(MouseEvent e) {
        jButton2.setText("No Help");
    }
}
