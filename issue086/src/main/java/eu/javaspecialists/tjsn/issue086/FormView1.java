package eu.javaspecialists.tjsn.issue086;

import javax.swing.*;

public abstract class FormView1 {
    private final JComponent mainUIComponent;

    public FormView1(JFrame owner) {
        // do some stuff...
        mainUIComponent = makeUI();
    }

    public abstract JComponent makeUI();

    public JComponent getMainUIComponent() {
        return mainUIComponent;
    }
}

