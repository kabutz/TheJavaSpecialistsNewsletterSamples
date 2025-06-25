package eu.javaspecialists.tjsn.issue074;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

public class MultiplicationTable3 extends AbstractTableFrame {
    public MultiplicationTable3() {
        super("Multiplication Table");
    }

    protected TableModel makeTableModel() {
        return new DefaultTableModel(100, 20) {
            public String getColumnName(int column) {
                return Integer.toString(column + 1);
            }

            public Object getValueAt(int row, int column) {
                return Integer.toString((row + 1) * (column + 1));
            }
        };
    }

    protected JLabel getDescription() {
        return new JLabel("Multiplication Table");
    }

    protected JButton[] makeButtons() {
        final JButton okButton = new JButton("OK");
        final JButton helpButton = new JButton("Help");
        okButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                okButton.setEnabled(false);
            }

            public void mouseExited(MouseEvent e) {
                okButton.setEnabled(true);
            }
        });
        helpButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                helpButton.setText("No Help");
            }
        });
        return new JButton[]{okButton, helpButton};
    }

    public static void main(String[] args) {
        MultiplicationTable3 frame = new MultiplicationTable3();
        frame.setSize(603, 483);
        Windows.centerOnScreen(frame);
        // instead of enabling the events and listening to window events:
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}
