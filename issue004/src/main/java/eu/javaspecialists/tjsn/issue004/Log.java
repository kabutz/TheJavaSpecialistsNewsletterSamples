package eu.javaspecialists.tjsn.issue004;

public class Log {
    public static void it(String msg) {
        //caller of it()
        String source = StackTrace2.getCallStack(1);
        System.out.println(source + " : " + msg);
    }
}
