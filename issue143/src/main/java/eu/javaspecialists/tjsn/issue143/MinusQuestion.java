package eu.javaspecialists.tjsn.issue143;

public class MinusQuestion extends MathsQuestion {
    public MinusQuestion(int upto) {
        int first = random(upto);
        int second = random(first - 1);
        setQuestion(first, second, '-', first - second);
    }
}