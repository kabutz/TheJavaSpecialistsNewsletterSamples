/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
