package eu.javaspecialists.tjsn.issue099;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public GUI(ComponentOrientation orientation) {
        super(orientation.isLeftToRight() ? "Left to Right" : "Right to Left");
        add(makeLabel("PageStart"), BorderLayout.PAGE_START);
        add(makeLabel("LineStart"), BorderLayout.LINE_START);
        add(makeLabel("LineEnd"), BorderLayout.LINE_END);
        add(makeLabel("PageEnd"), BorderLayout.PAGE_END);
        add(makeLabel("Centre"));
        applyComponentOrientation(orientation);
    }

    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createRaisedBevelBorder());
        return label;
    }

    private static void showGui(ComponentOrientation orientation) {
        GUI gui = new GUI(orientation);
        gui.setSize(640, 480);
        gui.setLocationByPlatform(true); // since JDK 5
        gui.setAlwaysOnTop(true);        // since JDK 5
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gui.setVisible(true);
    }

    public static void main(String[] args) {
        showGui(ComponentOrientation.LEFT_TO_RIGHT);
        showGui(ComponentOrientation.RIGHT_TO_LEFT);
    }
}
