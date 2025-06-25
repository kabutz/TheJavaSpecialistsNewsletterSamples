package eu.javaspecialists.tjsn.issue106;

import javax.swing.*;

public class TextAreaEditor extends DefaultCellEditor {
    public TextAreaEditor() {
        super(new JTextField());
        final JTextArea textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        editorComponent = scrollPane;

        delegate = new DefaultCellEditor.EditorDelegate() {
            public void setValue(Object value) {
                textArea.setText((value != null) ? value.toString() : "");
            }

            public Object getCellEditorValue() {
                return textArea.getText();
            }
        };
    }
}
