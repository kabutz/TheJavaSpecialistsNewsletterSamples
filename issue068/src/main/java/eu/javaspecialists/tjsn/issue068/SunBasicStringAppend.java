package eu.javaspecialists.tjsn.issue068;

public class SunBasicStringAppend {
    public SunBasicStringAppend() {
        String s = "";
        for (int i = 0; i < 100; i++) {
            s = new StringBuffer().append(s).append(i).toString();
        }
    }
}
