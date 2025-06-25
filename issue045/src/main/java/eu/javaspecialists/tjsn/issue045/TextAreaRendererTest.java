package eu.javaspecialists.tjsn.issue045;

import javax.swing.*;

public class TextAreaRendererTest extends JFrame {
    // The table has 10 rows and 3 columns
    private final JTable table = new JTable(10, 3);

    public TextAreaRendererTest() {
        // We use our cell renderer for the third column
        table.getColumnModel().getColumn(2).setCellRenderer(
                new TextAreaRenderer());
        // We hard-code the height of rows 0 and 5 to be 100
        table.setRowHeight(0, 100);
        table.setRowHeight(5, 100);
        // We put the table into a scrollpane and into a frame
        getContentPane().add(new JScrollPane(table));
        // We then set a few of the cells to our long example text
        String test = "The lazy dog jumped over the quick brown fox";
        table.getModel().setValueAt(test, 0, 0);
        table.getModel().setValueAt(test, 0, 1);
        table.getModel().setValueAt(test, 0, 2);
        table.getModel().setValueAt(test, 4, 0);
        table.getModel().setValueAt(test, 4, 1);
        table.getModel().setValueAt(test, 4, 2);
    }

    public static void main(String[] args) {
        TextAreaRendererTest test = new TextAreaRendererTest();
        test.setSize(600, 600);
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.show();
    }
}
