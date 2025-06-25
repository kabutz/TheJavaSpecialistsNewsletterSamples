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

package eu.javaspecialists.tjsn.issue294;

public class StringAppendingQuiz {
    public String appendPlain(String question,
                              String answer1,
                              String answer2) {
        return "<h1>" + question + "</h1><ol><li>" + answer1 +
                "</li><li>" + answer2 + "</li></ol>";
    }

    public String appendStringBuilder(String question,
                                      String answer1,
                                      String answer2) {
        return new StringBuilder().append("<h1>").append(question)
                .append("</h1><ol><li>").append(answer1)
                .append("</li><li>").append(answer2)
                .append("</li></ol>").toString();
    }

    public String appendStringBuilderSize(String question,
                                          String answer1,
                                          String answer2) {
        int len = 36 + question.length() + answer1.length() +
                answer2.length();
        return new StringBuilder(len).append("<h1>").append(question)
                .append("</h1><ol><li>").append(answer1)
                .append("</li><li>").append(answer2)
                .append("</li></ol>").toString();
    }
}
