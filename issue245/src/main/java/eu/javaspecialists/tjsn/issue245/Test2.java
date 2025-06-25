package eu.javaspecialists.tjsn.issue245;

public class Test2 {// JLS 15.7.1
    public static void main(String... args) {
        int a = 9;
        a += (a = 3); // first example
        System.out.println(a);
        int b = 9;
        b = b + (b = 3); // second example
        System.out.println(b);
    }
}
