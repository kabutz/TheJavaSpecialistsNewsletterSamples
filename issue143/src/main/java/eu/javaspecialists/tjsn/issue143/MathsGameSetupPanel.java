package eu.javaspecialists.tjsn.issue143;

import com.google.gwt.user.client.ui.*;

public class MathsGameSetupPanel extends VerticalPanel {
    private TextBox questions = new TextBox();
    private TextBox upToNumber = new TextBox();

    public MathsGameSetupPanel(final Maths owner) {
        setStyleName("game-style");

        questions.setText("10");
        upToNumber.setText("50");

        add(makeInput("Number of questions:", questions));
        add(makeInput("Up To Number:", upToNumber));
        add(new Button("Play Game!", new ClickListener() {
            public void onClick(Widget widget) {
                int numQues = Integer.parseInt(questions.getText());
                int upto = Integer.parseInt(upToNumber.getText());
                owner.playGame(numQues, upto);
            }
        }));
    }

    private Panel makeInput(String label, TextBox inputText) {
        HorizontalPanel inputPanel = new HorizontalPanel();
        inputPanel.add(new Label(label));
        inputPanel.add(inputText);
        return inputPanel;
    }
}
