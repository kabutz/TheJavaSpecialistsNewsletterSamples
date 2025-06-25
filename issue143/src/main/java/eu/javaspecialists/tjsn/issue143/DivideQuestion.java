package eu.javaspecialists.tjsn.issue143;


public class DivideQuestion extends MathsQuestion {
    public DivideQuestion(int upto) {
        int top = random(upto);
        int second = random((int) Math.sqrt(top));
        int first = top / second * second;
        setQuestion(first, second, '�', first / second);
    }
}
