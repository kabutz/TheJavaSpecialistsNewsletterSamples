package eu.javaspecialists.tjsn.issue196;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class DemoFrame extends JFrame {
    public DemoFrame() {
        super("DemoFrame");
        JButton button = new JButton("Cause Exception");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                throw new NullPointerException("brain is null");
            }
        });
        add(button);

        // This timer will run in the EDT
        javax.swing.Timer timer1 = new javax.swing.Timer(3000,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        throw new IllegalStateException("forgotten name");
                    }
                });
        timer1.start();

        // This timer will run in a normal thread
        java.util.Timer timer2 = new java.util.Timer();
        timer2.schedule(new TimerTask() {
            public void run() {
                throw new IllegalArgumentException("stop arguing!");
            }
        }, 6000);
    }

    public static void create() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DemoFrame frame = new DemoFrame();
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.setSize(500, 200);
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
    }
}
