package eu.javaspecialists.tjsn.issue081;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ThreadsDie extends JFrame {
    public ThreadsDie() {
        super("GUI Example");
        final JTextArea text = new JTextArea(14, 30);
        getContentPane().add(new JScrollPane(text));
        getContentPane().add(new JButton(new AbstractAction("Calculate") {
            private int countdown = 3;

            public void actionPerformed(ActionEvent e) {
                text.append("Event Queue Thread: " +
                        System.identityHashCode(Thread.currentThread()));
                text.append("\n");
                if (--countdown <= 0) {
                    throw new IllegalArgumentException();
                }
            }
        }), BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        ThreadsDie gui = new ThreadsDie();
        gui.pack();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.show();
    }
}
