package eu.javaspecialists.tjsn.issue014;

public class MindWarp {
    public static void main(String[] args) {
        System.out.println(
                "Romeo, Romeo, wherefore art thou oh Romero?");
    }

    private static final String OH_ROMEO =
            "Romeo, Romeo, wherefore art thou oh Romero?";
    private static final Warper warper = new Warper();
}