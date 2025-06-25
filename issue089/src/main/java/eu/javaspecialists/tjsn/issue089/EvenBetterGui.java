package eu.javaspecialists.tjsn.issue089;

import eu.javaspecialists.tjsn.issue081.*;

import javax.swing.*;

public class EvenBetterGui {
    public static void main(String... args) {
        Thread.setDefaultUncaughtExceptionHandler(
                new DefaultExceptionHandler());
        Gui gui = new Gui();
        gui.pack();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
    }
}
