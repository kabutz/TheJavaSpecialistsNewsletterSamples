package eu.javaspecialists.tjsn.issue069;

public class Test2 {
    public static void main(String[] args) {
        int i = 128;
        double d = Integer.MAX_VALUE + 12345.33;
        i = i + (int) d;
        System.out.println("i1 is " + i);
        i = 128;
        i = (int) (i + d);
        System.out.println("i2 is " + i);
    }
}
