package eu.javaspecialists.tjsn.issue266;

public class Threading02Holder {
    private static class Holder {
        private static final Object myObj = create();
    }

    public static Object retrieve() {
        return Holder.myObj;
    }

    private static Object create() {
        return new Object();
    }
}
