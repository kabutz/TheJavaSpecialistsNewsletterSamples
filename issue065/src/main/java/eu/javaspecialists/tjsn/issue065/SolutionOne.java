package eu.javaspecialists.tjsn.issue065;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * First attempt at a solution
 */
public class SolutionOne {
    private static JDialog createDialog(final JFrame frame) {
        final JDialog dialog = new JDialog(frame, "I'm Modal", true);
        dialog.getContentPane().add(
                new JLabel("I'm a busy modal dialog"));
        dialog.getContentPane().add(
                new JButton(new AbstractAction("Wait Cursor") {
                    public void actionPerformed(ActionEvent event) {
                        setWaitCursor(dialog);
                    }
                }));
        dialog.setSize(300, 200);
        return dialog;
    }

    public static void setWaitCursor(JDialog dialog) {
        System.out.println("Setting Wait cursor on frame");
        CursorToolkitTwo.startWaitCursor(
                ((JFrame) dialog.getOwner()).getRootPane());
        System.out.println("Setting Wait cursor on dialog");
        CursorToolkitTwo.startWaitCursor(dialog.getRootPane());
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Solution One");
        frame.getContentPane().add(
                new JLabel("I'm a Frame"), BorderLayout.NORTH);
        frame.getContentPane().add(
                new JButton(new AbstractAction("Show Dialog") {
                    public void actionPerformed(ActionEvent event) {
                        System.out.println("Showing dialog");
                        createDialog(frame).show();
                    }
                }));
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
