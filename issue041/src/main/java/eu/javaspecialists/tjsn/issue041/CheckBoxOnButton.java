package eu.javaspecialists.tjsn.issue041;

import javax.swing.*;
import java.awt.*;

public class CheckBoxOnButton {
    public static void main(String[] args) {
        JButton records = new JButton();
        records.setLayout(new BorderLayout());
        records.add(new JLabel("Show Records"), BorderLayout.NORTH);
        records.add(new JCheckBox("autoscroll"), BorderLayout.CENTER);
        JFrame f = new JFrame("CheckBox on Button");
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(records);
        f.setSize(200, 200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.show();
    }
}