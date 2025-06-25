package eu.javaspecialists.tjsn.issue074;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public abstract class AbstractTableFrame extends JFrame {
    public AbstractTableFrame(String title) {
        super(title);
        JPanel titlePanel = new JPanel();
        titlePanel.add(getDescription());
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        JTable table = new JTable(makeTableModel());
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton[] buttons = makeButtons();
        for (int i = 0; i < buttons.length; i++) {
            buttonPanel.add(buttons[i]);
        }
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    protected abstract TableModel makeTableModel();

    protected abstract JLabel getDescription();

    protected abstract JButton[] makeButtons();
}
