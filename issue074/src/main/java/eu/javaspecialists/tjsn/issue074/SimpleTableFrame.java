package eu.javaspecialists.tjsn.issue074;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

public class SimpleTableFrame extends AbstractTableFrame {
    public SimpleTableFrame() {
        super("Simple Table");
    }

    protected TableModel makeTableModel() {
        return new DefaultTableModel(3, 4);
    }

    protected JLabel getDescription() {
        return new JLabel("Empty Default Table");
    }

    protected JButton[] makeButtons() {
        return new JButton[]{new JButton(new AbstractAction("Close") {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        })};
    }

    public static void main(String[] args) {
        SimpleTableFrame frame = new SimpleTableFrame();
        frame.setSize(603, 483);
        Windows.centerOnScreen(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}
