package eu.javaspecialists.tjsn.issue074;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class MultiplicationTable extends JFrame {
    private final JButton okButton = new JButton("OK");
    private final JButton helpButton = new JButton("Help");
    private final JTable table = new JTable(new DefaultTableModel(100, 20) {
        public String getColumnName(int column) {
            return Integer.toString(column + 1);
        }

        public Object getValueAt(int row, int column) {
            return Integer.toString((row + 1) * (column + 1));
        }
    });

    //Construct the frame
    public MultiplicationTable() {
        super("Multiplication Table");
        setSize(603, 483);

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
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Multiplication Table"));
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(helpButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    // instead of Application1, we have the following few lines
    public static void main(String[] args) {
        MultiplicationTable frame = new MultiplicationTable();
        Windows.centerOnScreen(frame);
        // instead of enabling the events and listening to window events:
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}
