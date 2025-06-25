package eu.javaspecialists.tjsn.issue069;

public class Test3 {
    public static void main(String[] args) {
        int i = 128;
        double d = Integer.MAX_VALUE + 12345.33;
        i += d; // oops, forgot to cast!
        System.out.println("i is " + i);
    }
}
