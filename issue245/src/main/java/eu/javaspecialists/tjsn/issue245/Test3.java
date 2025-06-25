package eu.javaspecialists.tjsn.issue245;

public class Test3 { // JLS 15.7.1
    public static void main(String... args) {
        int j = 1;
        try {
            int i = forgetIt() / (j = 2);
        } catch (Exception e) {
            System.out.println("Now j = " + j);
        }
    }

    static int forgetIt() throws Exception {
        throw new Exception("I'm outta here!");
    }
}