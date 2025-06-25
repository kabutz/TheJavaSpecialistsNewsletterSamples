package eu.javaspecialists.tjsn.issue143;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.*;

public class Maths implements EntryPoint {
    private Panel currentPanel;

    public void onModuleLoad() {
        setupGame();
    }

    public void setupGame() {
        setPanel(new MathsGameSetupPanel(this));
    }

    public void playGame(int questions, int upto) {
        setPanel(new MathsGamePlayPanel(this, questions, upto));
    }

    private void setPanel(Panel panel) {
        if (currentPanel != null) {
            RootPanel.get().remove(currentPanel);
        }
        currentPanel = panel;
        RootPanel.get().add(currentPanel);
    }
}
