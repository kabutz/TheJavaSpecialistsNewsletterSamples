package eu.javaspecialists.tjsn.issue110;

public class LabelTryFinally {
    public static void main(String[] args) {
        what:
        try {
            if (true) {
                break what; // you can jump out of the try block, but
                // of course the finally first is executed
            }
            System.out.println("in try");
        } finally {
            System.out.println("in finally");
        }
    }
}
