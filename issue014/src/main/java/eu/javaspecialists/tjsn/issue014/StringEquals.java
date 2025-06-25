package eu.javaspecialists.tjsn.issue014;

public class StringEquals {
    public static void main(String[] args) {
        System.out.println("hi there".equals("cheers !"));
    }

    private static final String greeting = "hi there";
    private static final Warper warper = new Warper();
}