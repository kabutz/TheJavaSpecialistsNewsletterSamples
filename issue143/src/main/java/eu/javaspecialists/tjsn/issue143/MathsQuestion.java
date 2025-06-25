package eu.javaspecialists.tjsn.issue143;

public abstract class MathsQuestion {
    private int answer;
    private String question;

    protected int random(int max) {
        return (int) (Math.random() * max) + 1;
    }

    protected void setQuestion(int first, int second,
                               char operator, int answer) {
        this.question = first + " " + operator + " " + second + " = ";
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }
}
