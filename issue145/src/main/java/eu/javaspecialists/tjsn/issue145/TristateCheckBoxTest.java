package eu.javaspecialists.tjsn.issue145;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TristateCheckBoxTest {
    public static void main(String args[]) throws Exception {
        JFrame frame = new JFrame("TristateCheckBoxTest");
        frame.setLayout(new GridLayout(0, 1, 15, 15));
        UIManager.LookAndFeelInfo[] lfs =
                UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo lf : lfs) {
            System.out.println("Look&Feel " + lf.getName());
            UIManager.setLookAndFeel(lf.getClassName());
            frame.add(makePanel());
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static JPanel makePanel() {
        final TristateCheckBox tristateBox = new TristateCheckBox(
                "Tristate checkbox");
        tristateBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                switch (tristateBox.getState()) {
                    case SELECTED:
                        System.out.println("Selected");
                        break;
                    case DESELECTED:
                        System.out.println("Not Selected");
                        break;
                    case INDETERMINATE:
                        System.out.println("Tristate Selected");
                        break;
                }
            }
        });
        tristateBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e);
            }
        });
        final JCheckBox normalBox = new JCheckBox("Normal checkbox");
        normalBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e);
            }
        });

        final JCheckBox enabledBox = new JCheckBox("Enable", true);
        enabledBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                tristateBox.setEnabled(enabledBox.isSelected());
                normalBox.setEnabled(enabledBox.isSelected());
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel(UIManager.getLookAndFeel().getName()));
        panel.add(tristateBox);
        panel.add(normalBox);
        panel.add(enabledBox);
        return panel;
    }
}
