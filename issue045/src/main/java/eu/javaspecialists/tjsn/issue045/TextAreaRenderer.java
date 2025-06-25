package eu.javaspecialists.tjsn.issue045;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class TextAreaRenderer extends JTextArea
        implements TableCellRenderer {

    public TextAreaRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public Component getTableCellRendererComponent(JTable jTable,
                                                   Object obj, boolean isSelected, boolean hasFocus, int row,
                                                   int column) {
        setText((String) obj);
        return this;
    }
}
