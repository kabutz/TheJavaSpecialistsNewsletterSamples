package eu.javaspecialists.tjsn.issue143;

public class PlusQuestion extends MathsQuestion {
    public PlusQuestion(int upto) {
        int first = random(upto - (upto / 10));
        int second = random(upto - first - 1);
        setQuestion(first, second, '+', first + second);
    }
}