package eu.javaspecialists.tjsn.issue143;

import com.google.gwt.user.client.ui.*;

public class MathsGamePlayPanel extends VerticalPanel {
    private MathsGameBean mathsGame;
    private MathsBean maths;
    private TextBox answerBox = new TextBox();
    private Label questionLabel = new Label();
    private Label remark = new Label();
    private Maths owner;

    public MathsGamePlayPanel(Maths owner, int questions, int upto) {
        setStyleName("game-style");
        this.owner = owner;
        mathsGame = new MathsGameBean(questions, upto);
        maths = mathsGame.next();

        questionLabel.setText(maths.getQuestion());

        answerBox.setMaxLength(10);
        answerBox.setWidth("60px");
        answerBox.addKeyboardListener(new KeyboardListenerAdapter() {
            public void onKeyPress(Widget widget, char c, int i) {
                if (c == 13) {
                    checkAnswer();
                }
            }
        });

        Panel questionPanel = new HorizontalPanel();
        questionPanel.add(questionLabel);
        questionPanel.add(answerBox);

        add(questionPanel);
        add(remark);
    }

    private void checkAnswer() {
        int answer = Integer.parseInt(answerBox.getText());
        if (maths.checkAnswer(answer)) {
            remark.setText("Well Done!");
            remark.setStyleName("correct-answer");
        } else {
            remark.setText("Nope! " + maths.getQuestion() +
                    maths.getAnswer() + " not " + answer);
            remark.setStyleName("wrong-answer");
        }
        answerBox.setText("");

        if (mathsGame.hasNext()) {
            maths = mathsGame.next();
            questionLabel.setText(maths.getQuestion());
        } else {
            add(new Label(mathsGame.getScore() + " / " +
                    mathsGame.getTotalQuestions()));
            add(new Label(mathsGame.getRate() + " answers per minute"));
            add(new Button("Play again!", new ClickListener() {
                public void onClick(Widget widget) {
                    owner.setupGame();
                }
            }));
            answerBox.setEnabled(false);
        }
    }
}
