package eu.javaspecialists.tjsn.issue262;

public abstract class BruteForceBase {
    protected static final byte[] alphabet = new byte[26 * 2];

    static {
        for (int i = 0; i < 26; i++) {
            alphabet[i] = (byte) ('A' + i);
            alphabet[i + 26] = (byte) ('a' + i);
        }
        System.out.println("alphabet = " + new String(alphabet));
    }
}
