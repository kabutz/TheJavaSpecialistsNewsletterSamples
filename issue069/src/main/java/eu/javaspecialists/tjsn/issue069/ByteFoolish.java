package eu.javaspecialists.tjsn.issue069;

public class ByteFoolish {
    public static void main(String[] args) {
        int i = 128;
        byte b = 0;
        b |= i;
        System.out.println("Byte is " + b);
        i = 0;
        i |= b;
        System.out.println("Int is " + i);
    }
}
