package eu.javaspecialists.tjsn.issue101;

import javax.swing.*;
import java.awt.*;

public class StrangeProblem extends JFrame {
    static {
        new StrangeProblem();
    }

    private static void staticMethod() {
        System.out.println("This is never reached");
    }

    private StrangeProblem() {
        getContentPane().add(new MyJDesktopPane());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        // If commented out, program works fine, otherwise it hangs
        new JInternalFrame();
    }

    private class MyJDesktopPane extends JDesktopPane {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println("We will now call the static method...");
            staticMethod();
            System.out.println("Static method was called.");
        }
    }

    public static void main(String[] args) {
    }
}
