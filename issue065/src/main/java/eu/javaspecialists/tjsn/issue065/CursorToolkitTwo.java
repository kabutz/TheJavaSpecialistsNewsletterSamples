package eu.javaspecialists.tjsn.issue065;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Basic CursorToolkit that swallows mouseclicks
 */
public class CursorToolkitTwo implements Cursors {
    private static final MouseAdapter mouseAdapter =
            new MouseAdapter() {
            };

    private CursorToolkitTwo() {
    }

    /**
     * Sets cursor for specified component to Wait cursor
     */
    public static void startWaitCursor(JComponent component) {
        RootPaneContainer root =
                ((RootPaneContainer) component.getTopLevelAncestor());
        root.getGlassPane().setCursor(WAIT_CURSOR);
        root.getGlassPane().addMouseListener(mouseAdapter);
        root.getGlassPane().setVisible(true);
    }

    /**
     * Sets cursor for specified component to normal cursor
     */
    public static void stopWaitCursor(JComponent component) {
        RootPaneContainer root =
                ((RootPaneContainer) component.getTopLevelAncestor());
        root.getGlassPane().setCursor(DEFAULT_CURSOR);
        root.getGlassPane().removeMouseListener(mouseAdapter);
        root.getGlassPane().setVisible(false);
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Test App");
        frame.getContentPane().add(
                new JLabel("I'm a Frame"), BorderLayout.NORTH);
        frame.getContentPane().add(
                new JButton(new AbstractAction("Wait Cursor") {
                    public void actionPerformed(ActionEvent event) {
                        System.out.println("Setting Wait cursor on frame");
                        startWaitCursor(frame.getRootPane());
                    }
                }));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}
