package eu.javaspecialists.tjsn.issue030;


import javax.swing.*;
import java.awt.event.*;

public class PreferencesEditorDemo {
    public static void main(String... args) {
        JFrame frame = new JFrame("PreferencesEditorDemo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        menu.add(new JMenuItem(
                new AbstractAction("Preferences") {
                    public void actionPerformed(ActionEvent e) {
                        PreferencesEditor dialog = new PreferencesEditor(frame, "Our Preferences");
                        dialog.setLocationRelativeTo(frame);
                        dialog.setModal(true);
                        dialog.setVisible(true);
                    }
                }));
        menu.add(new JMenuItem(new AbstractAction("Exit") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }));
        frame.setSize(500, 400);
        frame.setJMenuBar(menuBar);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
