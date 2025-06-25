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

public class MathsGameBean {
    private int currentQuestion = 0;
    private long start = System.currentTimeMillis();
    private final MathsBean[] questions;

    public MathsGameBean(int questions, int upto) {
        this.questions = new MathsBean[questions];
        for (int i = 0; i < questions; i++) {
            this.questions[i] = new MathsBean(upto);
        }
    }

    public boolean hasNext() {
        return currentQuestion < questions.length;
    }

    public MathsBean next() {
        return questions[currentQuestion++];
    }

    public int getScore() {
        int correct = 0;
        for (int i = 0; i < questions.length; i++) {
            correct += questions[i].isCorrect() ? 1 : 0;
        }
        return correct;
    }

    public int getTotalQuestions() {
        return questions.length;
    }

    public int getSeconds() {
        return (int) ((System.currentTimeMillis() - start + 500) / 1000);
    }

    public int getRate() {
        return getTotalQuestions() * 60 / getSeconds();
    }
}
