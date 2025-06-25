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

package eu.javaspecialists.tjsn.issue101;

import javax.swing.*;
import java.awt.*;

public class StrangeProblemFixed extends JFrame {
    static {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StrangeProblemFixed();
            }
        });

    }

    private static void staticMethod() {
        System.out.println("This is now reached");
    }

    private StrangeProblemFixed() {
        getContentPane().add(new MyJDesktopPane());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        // If commented out, program works fine, otherwise it hangs
        new JInternalFrame();
    }

    private class MyJDesktopPane extends JDesktopPane {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println("We will now call the static method...");
            staticMethod();
            System.out.println("Static method was called.");
        }
    }

    public static void main(String[] args) {
    }
}
