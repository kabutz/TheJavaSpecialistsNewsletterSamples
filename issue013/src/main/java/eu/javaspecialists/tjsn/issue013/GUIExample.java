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

public class GUIExample extends JFrame {
    public static final int PORT = 4123;
    private static final ComponentSerializer compser =
            new ComponentSerializer();
    private JScrollPane scrollPane;

    public GUIExample() {
        super("GUIExample Frame");
        scrollPane = new JScrollPane(new JTable(3, 4));
        getContentPane().add(scrollPane);
        getContentPane().add(new JButton(
                new AbstractAction("Serialize Table") {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Now we serialize synchronously");
                        try {
                            Socket socket = new Socket("localhost", PORT);
                            compser.write(scrollPane, socket.getOutputStream());
                            socket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }), BorderLayout.SOUTH);
        setSize(400, 200);
        show();
    }

    public static void main(String[] args) throws Exception {
        GUIExample ex = new GUIExample();
        ex.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
