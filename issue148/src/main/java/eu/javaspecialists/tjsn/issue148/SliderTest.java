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

package eu.javaspecialists.tjsn.issue148;

import javax.swing.*;
import java.awt.*;

public class SliderTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.getContentPane().setLayout(new GridLayout(0, 1));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(makeSlider("Without Snapping"));
                SliderSnap.init();
                UIManager.LookAndFeelInfo[] infos =
                        UIManager.getInstalledLookAndFeels();
                for (int i = 0; i < infos.length; i++) {
                    UIManager.LookAndFeelInfo info = infos[i];
                    try {
                        UIManager.setLookAndFeel(info.getClassName());
                        JComponent slider = makeSlider(info.getClassName());
                        frame.getContentPane().add(slider);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    private static JComponent makeSlider(String title) {
        JPanel panel = new JPanel();
        JSlider slider = new JSlider(-50, 50, 0);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(20);
        slider.setSnapToTicks(true);
        panel.add(slider);
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }
}
