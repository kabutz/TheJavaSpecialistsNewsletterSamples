package eu.javaspecialists.tjsn.issue081;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame {
    private static final int[] DAYS_PER_MONTH =
            {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public Gui() {
        super("GUI Example");
        final JTextArea text = new JTextArea(14, 30);
        getContentPane().add(new JScrollPane(text));
        getContentPane().add(new JButton(new AbstractAction("Calculate") {
            public void actionPerformed(ActionEvent e) {
                text.setText("");
                for (int i = 0; i <= DAYS_PER_MONTH.length; i++) {
                    text.append("Month " + (i + 1) + ": " +
                            DAYS_PER_MONTH[i] + "\n");
                }
            }
        }), BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.pack();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.show();
    }
}
