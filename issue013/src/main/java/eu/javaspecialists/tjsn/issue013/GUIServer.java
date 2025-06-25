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
import java.io.*;
import java.net.*;

public class GUIServer {
    public static final int PORT = 4123;
    private static final ComponentSerializer compser =
            new ComponentSerializer();

    public GUIServer() throws IOException {
        System.out.println("Super-duper GUI SERVER started");
        ServerSocket ss = new ServerSocket(PORT);
        while (true) {
            Socket socket = ss.accept();
            try {
                JFrame frame = new JFrame(
                        "Component received from " + socket);
                Component comp = compser.read(socket.getInputStream());
                frame.getContentPane().add(comp);
                frame.pack();
                frame.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new GUIServer();
    }
}