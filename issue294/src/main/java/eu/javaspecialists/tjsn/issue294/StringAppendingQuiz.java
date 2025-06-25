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
