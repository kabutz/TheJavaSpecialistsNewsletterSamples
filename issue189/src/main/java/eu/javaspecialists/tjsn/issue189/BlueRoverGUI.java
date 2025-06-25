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

package eu.javaspecialists.tjsn.issue189;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class BlueRoverGUI extends JFrame {
    public BlueRoverGUI(final RoverModel model) {
        super("BlueRoverGUI");
        JLabel label = new JLabel("<html><p>Please use the arrow " +
                "keys to control the NXT.<p>Press b for blue, " +
                "g for green and r for red.<p>Press n to turn the " +
                "colors off.<p>Close the program to exit NXT " +
                "program.</html>");
        add(label);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        model.forward();
                        break;
                    case KeyEvent.VK_DOWN:
                        model.backward();
                        break;
                    case KeyEvent.VK_LEFT:
                        model.left();
                        break;
                    case KeyEvent.VK_RIGHT:
                        model.right();
                        break;
                    case KeyEvent.VK_B:
                        model.blue();
                        break;
                    case KeyEvent.VK_G:
                        model.green();
                        break;
                    case KeyEvent.VK_R:
                        model.red();
                        break;
                    case KeyEvent.VK_N:
                        model.noLights();
                        break;
                }
            }
        });
    }

    public static void main(String... args) throws IOException {
        final RoverModel model = new RoverModel();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BlueRoverGUI cont = new BlueRoverGUI(model);
                cont.setSize(500, 300);
                cont.setDefaultCloseOperation(EXIT_ON_CLOSE);
                cont.setVisible(true);

                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        try {
                            model.exit();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                });
            }
        });
    }
}
