package eu.javaspecialists.tjsn.issue041;

import javax.swing.*;
import java.awt.*;

public class MultilineButton {
    public static void main(String[] args) {
        JButton button = new JButton();
        // It is important to set the layout manager of the button
        button.setLayout(new GridLayout(0, 1));
        // Then we simply add components to it!
        button.add(new JLabel("This is a", JLabel.CENTER));
        button.add(new JLabel("multiline", JLabel.CENTER));
        button.add(new JLabel("button.", JLabel.CENTER));
        JFrame f = new JFrame("Multi-Lined Button");
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(button);
        f.setSize(100, 100);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.show();
    }
}