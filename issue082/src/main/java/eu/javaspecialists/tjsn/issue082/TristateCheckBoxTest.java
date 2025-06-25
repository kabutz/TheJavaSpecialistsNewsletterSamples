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

package eu.javaspecialists.tjsn.issue082;

import javax.swing.*;
import java.awt.*;

public class TristateCheckBoxTest {
    public static void main(String args[]) throws Exception {
        JFrame frame = new JFrame("TristateCheckBoxTest");
        frame.getContentPane().setLayout(new GridLayout(0, 1, 5, 5));
        final TristateCheckBox swingBox = new TristateCheckBox(
                "Testing the tristate checkbox");
        swingBox.setMnemonic('T');
        frame.getContentPane().add(swingBox);
        frame.getContentPane().add(new JCheckBox(
                "The normal checkbox"));
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        final TristateCheckBox winBox = new TristateCheckBox(
                "Testing the tristate checkbox",
                TristateCheckBox.SELECTED);
        frame.getContentPane().add(winBox);
        final JCheckBox winNormal = new JCheckBox(
                "The normal checkbox");
        frame.getContentPane().add(winNormal);
        // wait for 3 seconds, then enable all check boxes
        new Thread() {
            {start();}

            public void run() {
                try {
                    winBox.setEnabled(false);
                    winNormal.setEnabled(false);
                    Thread.sleep(3000);
                    winBox.setEnabled(true);
                    winNormal.setEnabled(true);
                } catch (InterruptedException ex) {}
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.show();
    }
}
