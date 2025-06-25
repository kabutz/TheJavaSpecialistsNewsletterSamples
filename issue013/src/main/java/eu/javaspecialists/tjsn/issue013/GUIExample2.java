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

package eu.javaspecialists.tjsn.issue013;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class GUIExample2 extends JFrame {
    public static final int PORT = 4123;
    private static final ComponentSerializer compser =
            new ComponentSerializer();
    private JPanel personalData;

    public GUIExample2() {
        super("Asynchronous Sending GUIExample2 Frame");
        personalData = new JPanel(new GridLayout(0, 2, 5, 5));
        personalData.add(new JLabel("Name: "));
        personalData.add(new JTextField());
        personalData.add(new JLabel("Age: "));
        personalData.add(new JTextField());
        getContentPane().add(personalData, BorderLayout.NORTH);
        getContentPane().add(new JButton(
                new AbstractAction("Serialize Personal Data") {
                    public void actionPerformed(ActionEvent e) {
                        asyncSerialize(personalData);
                    }
                }), BorderLayout.SOUTH);
        setSize(400, 200);
        show();
    }

    private void asyncSerialize(final Component comp) {
        new Thread() {
            {start();} // start from initializer block

            public void run() {
                try {
                    Socket socket = new Socket("localhost", PORT);
                    compser.write(comp, socket.getOutputStream());
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) throws Exception {
        GUIExample2 ex = new GUIExample2();
        ex.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}