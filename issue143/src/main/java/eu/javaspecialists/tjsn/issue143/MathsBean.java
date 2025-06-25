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
