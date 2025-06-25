package eu.javaspecialists.tjsn.issue041;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class TreeOnButton {
    public static void main(String[] args) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());

        final JLabel buttonText = new JLabel("Press me",
                JLabel.CENTER);
        button.add(buttonText, BorderLayout.NORTH);

        JTree tree = new JTree();
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                buttonText.setText("Press for " +
                        e.getPath().getLastPathComponent());
            }
        });
        button.add(tree, BorderLayout.CENTER);

        JFrame f = new JFrame("Tree on Button");
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(button);
        f.setSize(500, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.show();
    }
}