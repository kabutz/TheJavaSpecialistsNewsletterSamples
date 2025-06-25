package eu.javaspecialists.tjsn.issue069;

public class Test1 {
    public static void main(String[] args) {
        int i = 128;
        double d = 3.3234123;
        // i = i + d; // <-- incompatible types: possible lossy conversion from double to int
        System.out.println("i is " + i);
    }
}
