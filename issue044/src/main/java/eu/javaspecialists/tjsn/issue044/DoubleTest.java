package eu.javaspecialists.tjsn.issue044;

public class DoubleTest {
    public static void main(String[] args) {
        System.out.println(2.71828182845905 - 2.71828182845904);
        System.out.println(2.71828182845905 - 2.71828182845904 +
                0.00000000000001);
        System.out.println(2.71828182845905 == (2.71828182845904 +
                0.00000000000001));
    }
}
