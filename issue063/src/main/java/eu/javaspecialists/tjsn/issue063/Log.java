package eu.javaspecialists.tjsn.issue063;

public class Log {
    private static CallDetective detective =
            CallDetective.Factory.makeCallDetective();

    public static void it(String msg) {
        String source = detective.findCaller(1); //caller of it()
        System.out.println(source + " : " + msg);
    }
}
