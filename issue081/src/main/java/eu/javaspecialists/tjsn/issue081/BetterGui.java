package eu.javaspecialists.tjsn.issue081;

import javax.swing.*;

public class BetterGui {
    public static void main(String[] args) {
        ThreadGroup exceptionThreadGroup = new ExceptionGroup();
        new Thread(exceptionThreadGroup, "Init thread") {
            public void run() {
                Gui gui = new Gui();
                gui.pack();
                gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gui.show();
            }
        }.start();
    }
}
