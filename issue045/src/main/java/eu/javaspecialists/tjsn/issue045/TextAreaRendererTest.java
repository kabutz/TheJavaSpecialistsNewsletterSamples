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

package eu.javaspecialists.tjsn.issue045;

import javax.swing.*;

public class TextAreaRendererTest extends JFrame {
    // The table has 10 rows and 3 columns
    private final JTable table = new JTable(10, 3);

    public TextAreaRendererTest() {
        // We use our cell renderer for the third column
        table.getColumnModel().getColumn(2).setCellRenderer(
                new TextAreaRenderer());
        // We hard-code the height of rows 0 and 5 to be 100
        table.setRowHeight(0, 100);
        table.setRowHeight(5, 100);
        // We put the table into a scrollpane and into a frame
        getContentPane().add(new JScrollPane(table));
        // We then set a few of the cells to our long example text
        String test = "The lazy dog jumped over the quick brown fox";
        table.getModel().setValueAt(test, 0, 0);
        table.getModel().setValueAt(test, 0, 1);
        table.getModel().setValueAt(test, 0, 2);
        table.getModel().setValueAt(test, 4, 0);
        table.getModel().setValueAt(test, 4, 1);
        table.getModel().setValueAt(test, 4, 2);
    }

    public static void main(String[] args) {
        TextAreaRendererTest test = new TextAreaRendererTest();
        test.setSize(600, 600);
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.show();
    }
}
