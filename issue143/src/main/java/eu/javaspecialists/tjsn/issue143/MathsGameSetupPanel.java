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
