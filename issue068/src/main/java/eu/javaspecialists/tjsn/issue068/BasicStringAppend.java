package eu.javaspecialists.tjsn.issue068;

public class BasicStringAppend {
    public BasicStringAppend() {
        String s = "";
        for (int i = 0; i < 100; i++) {
            s += i;
        }
    }
}
