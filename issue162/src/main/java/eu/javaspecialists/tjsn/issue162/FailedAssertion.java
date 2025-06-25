package eu.javaspecialists.tjsn.issue162;

public class FailedAssertion {
    public static void main(String[] args) {
        try {
            assert 4 == 5 : "4 is not 5, we thought it was";
        } catch (AssertionError ae) {
            System.out.println("We are ignoring this: " + ae);
        }
        System.out.println("The main thread happily carries on ...");
    }
}
