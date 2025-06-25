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

public class MathsBean {
    private final MathsQuestion strategy;
    private boolean correct = false;

    public MathsBean(int upto) {
        strategy = makeRandomQuestion(upto);
    }

    private MathsQuestion makeRandomQuestion(int upto) {
        switch ((int) (Math.random() * 4)) {
            default:
            case 0:
                return new PlusQuestion(upto);
            case 1:
                return new MinusQuestion(upto);
            case 2:
                return new TimesQuestion(upto);
            case 3:
                return new DivideQuestion(upto);
        }
    }

    public String getQuestion() {
        return strategy.getQuestion();
    }

    public boolean checkAnswer(int answer) {
        return correct = strategy.getAnswer() == answer;
    }

    public int getAnswer() {
        return strategy.getAnswer();
    }

    public boolean isCorrect() {
        return correct;
    }
}
