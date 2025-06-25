package eu.javaspecialists.tjsn.issue114;

public class StaticFinalTestClient {
    public static void main(String[] args) {
        System.out.println(StaticFinalTest.LITERAL);
        System.out.println(StaticFinalTest.LITERAL_PLUS);
        System.out.println(StaticFinalTest.LITERAL_NEW);
        System.out.println(StaticFinalTest.LITERAL_CONCAT);
    }
}
