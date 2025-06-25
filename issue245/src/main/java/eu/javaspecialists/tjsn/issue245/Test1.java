package eu.javaspecialists.tjsn.issue245;

public class Test1 { // JLS 15.7.1
    public static void main(String... args) {
        int i = 2;
        int j = (i = 3) * i;
        System.out.println(j);
    }
}