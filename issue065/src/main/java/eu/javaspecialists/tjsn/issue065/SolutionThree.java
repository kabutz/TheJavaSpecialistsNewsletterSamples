package eu.javaspecialists.tjsn.issue065;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Here is a solution where we make the modal dialog non-modal.
 * Since we disable mouse clicks on the frame, it is actually
 * the same as a modal dialog.
 */
public class SolutionThree {
    private static JDialog createDialog(final JFrame frame) {
        final WaitEnabledDialog dialog =
                new WaitEnabledDialog(frame, "I'm not Modal");
        dialog.getContentPane().add(
                new JLabel("I'm a busy non-modal dialog"));
        dialog.getContentPane().add(
                new JButton(new AbstractAction("Wait Cursor") {
                    public void actionPerformed(ActionEvent event) {
                        setWaitCursor(dialog);
                    }
                }));
        dialog.setSize(300, 200);
        return dialog;
    }

    public static void setWaitCursor(final JDialog dialog) {
        System.out.println("Setting Wait cursor on dialog");
        CursorToolkitTwo.startWaitCursor(dialog.getRootPane());
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Solution Three");
        frame.getContentPane().add(
                new JLabel("I'm a Frame"), BorderLayout.NORTH);
        frame.getContentPane().add(
                new JButton(new AbstractAction("Show Dialog") {
                    public void actionPerformed(ActionEvent event) {
                        JDialog dialog = createDialog(frame);
                        System.out.println("Showing dialog");
                        dialog.show();
                    }
                }));
        frame.setSize(800, 600);
        frame.show();
    }
}
