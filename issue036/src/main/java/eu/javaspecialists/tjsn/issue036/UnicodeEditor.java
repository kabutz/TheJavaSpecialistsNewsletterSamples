package eu.javaspecialists.tjsn.issue036;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class UnicodeEditor extends JFrame {
    private JPanel buttonPanel;
    private JScrollPane editorPanel;
    private JTextArea editor;
    private final String filename;
    private final String encoding;

    public UnicodeEditor(String filename, String encoding)
            throws IOException {
        this.filename = filename;
        this.encoding = encoding;
        getContentPane().add(getButtonPanel(), BorderLayout.NORTH);
        getContentPane().add(getEditorPanel(), BorderLayout.CENTER);
        load();
    }

    protected JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            JButton unicodeInsert = new JButton("Insert Unicode:");
            final JTextField unicodeField = new JTextField(8);
            JButton saveExit = new JButton("Save & Exit");
            unicodeInsert.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getEditor().insert(
                            "" + (char) Integer.parseInt(unicodeField.getText()),
                            getEditor().getCaretPosition());
                }
            });
            saveExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        save();
                        System.exit(0);
                    } catch (IOException ex) {ex.printStackTrace();}
                }
            });
            buttonPanel.add(unicodeInsert);
            buttonPanel.add(unicodeField);
            buttonPanel.add(saveExit);
        }
        return buttonPanel;
    }

    protected JTextArea getEditor() {
        if (editor == null) {
            editor = new JTextArea();
        }
        return editor;
    }

    protected JScrollPane getEditorPanel() {
        if (editorPanel == null) {
            editorPanel = new JScrollPane(getEditor());
        }
        return editorPanel;
    }

    protected void load() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(filename), encoding));
        StringBuffer buf = new StringBuffer();
        int i;
        while ((i = in.read()) != -1) buf.append((char) i);
        in.close();
        getEditor().setText(buf.toString());
    }

    protected void save() throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename), encoding));
        char[] text = getEditor().getText().toCharArray();
        for (int i = 0; i < text.length; i++) out.write(text[i]);
        out.close();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1)
            throw new IllegalArgumentException(
                    "usage: UnicodeEditor filename [encoding]");
        String encoding = (args.length == 2) ? args[1] : "UTF-16BE";
        UnicodeEditor editor = new UnicodeEditor(args[0], encoding);
        editor.setSize(500, 500);
        editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editor.show();
    }
}
