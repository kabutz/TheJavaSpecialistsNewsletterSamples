package eu.javaspecialists.tjsn.issue066;

public class TryCatch {
    public static boolean test1(Object o) {
        try {
            Integer i = (Integer) o;
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean test2(Object o) {
        if (o instanceof Integer) {
            Integer i = (Integer) o;
            return false;
        } else {
            return true;
        }
    }
}
