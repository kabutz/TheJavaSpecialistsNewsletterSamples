package eu.javaspecialists.tjsn.issue068;

public class IbmBasicStringAppend {
    public IbmBasicStringAppend() {
        String s = "";
        for (int i = 0; i < 100; i++) {
            s = new StringBuffer(String.valueOf(s)).append(i).toString();
        }
    }
}
