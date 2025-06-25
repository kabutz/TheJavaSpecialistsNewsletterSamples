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

package eu.javaspecialists.tjsn.issue099;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public GUI(ComponentOrientation orientation) {
        super(orientation.isLeftToRight() ? "Left to Right" : "Right to Left");
        add(makeLabel("PageStart"), BorderLayout.PAGE_START);
        add(makeLabel("LineStart"), BorderLayout.LINE_START);
        add(makeLabel("LineEnd"), BorderLayout.LINE_END);
        add(makeLabel("PageEnd"), BorderLayout.PAGE_END);
        add(makeLabel("Centre"));
        applyComponentOrientation(orientation);
    }

    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createRaisedBevelBorder());
        return label;
    }

    private static void showGui(ComponentOrientation orientation) {
        GUI gui = new GUI(orientation);
        gui.setSize(640, 480);
        gui.setLocationByPlatform(true); // since JDK 5
        gui.setAlwaysOnTop(true);        // since JDK 5
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gui.setVisible(true);
    }

    public static void main(String[] args) {
        showGui(ComponentOrientation.LEFT_TO_RIGHT);
        showGui(ComponentOrientation.RIGHT_TO_LEFT);
    }
}
