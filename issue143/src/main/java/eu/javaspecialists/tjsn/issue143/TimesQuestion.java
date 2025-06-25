package eu.javaspecialists.tjsn.issue143;

public class TimesQuestion extends MathsQuestion {
    public TimesQuestion(int upto) {
        int first = random((int) Math.sqrt(upto));
        int num = random(upto);
        int second = num / first;
        setQuestion(first, second, '�', first * second);
    }
}