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
