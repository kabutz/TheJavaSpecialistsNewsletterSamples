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
