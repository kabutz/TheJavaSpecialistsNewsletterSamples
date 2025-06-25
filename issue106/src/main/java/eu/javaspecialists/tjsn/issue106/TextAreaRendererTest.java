package eu.javaspecialists.tjsn.issue106;

import javax.swing.*;
import javax.swing.table.*;

public class TextAreaRendererTest extends JFrame {
    private final JTable table = new JTable(10, 4);

    public TextAreaRendererTest() {
        super(System.getProperty("java.vm.version"));

        // We use our cell renderer for columns 1, 2, 3
        TableColumnModel cmodel = table.getColumnModel();
        TextAreaRenderer textAreaRenderer = new TextAreaRenderer();

        cmodel.getColumn(1).setCellRenderer(textAreaRenderer);
        cmodel.getColumn(2).setCellRenderer(new TextAreaRenderer());
        // I am demonstrating that you can have several renderers in
        // one table, and they communicate with one another in
        // deciding the row height.
        cmodel.getColumn(3).setCellRenderer(textAreaRenderer);
        TextAreaEditor textEditor = new TextAreaEditor();
        cmodel.getColumn(1).setCellEditor(textEditor);
        cmodel.getColumn(2).setCellEditor(textEditor);
        cmodel.getColumn(3).setCellEditor(textEditor);

        String test = "The lazy dog jumps over the quick brown fox";

        for (int column = 0; column < 4; column++) {
            table.setValueAt(test, 0, column);
            table.setValueAt(test, 4, column);
        }
        test = test + test + test + test + test + test + test + test;
        table.setValueAt(test, 4, 2);

        getContentPane().add(new JScrollPane(table));
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TextAreaRendererTest();
    }
}
