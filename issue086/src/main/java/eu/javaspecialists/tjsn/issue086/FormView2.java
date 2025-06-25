package eu.javaspecialists.tjsn.issue086;

import javax.swing.*;

public abstract class FormView2 {
    private final JComponent mainUIComponent;

    public FormView2(JFrame owner, Object subclassParameters) {
        // do some stuff...
        mainUIComponent = makeUI(subclassParameters);
    }

    public abstract JComponent makeUI(Object subclassParameters);

    public JComponent getMainUIComponent() {
        return mainUIComponent;
    }
}

