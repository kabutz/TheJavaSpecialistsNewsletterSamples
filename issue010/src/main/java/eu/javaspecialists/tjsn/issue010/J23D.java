package eu.javaspecialists.tjsn.issue010;

import java.awt.*;

public class J23D {
    // This cursor class when loaded now starts the GUI event threads!
    private static Cursor s_waitCursor =
            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);

    public void log(String _msg) {
        System.out.println(_msg);
    }

    public static void main(String[] _args) {
        new J23D().log("That's all folks!");
    }
}