package eu.javaspecialists.tjsn.issue049;

import com.sun.javadoc.*;

public class CommentChecker {
    public static boolean start(RootDoc root) {
        ClassDoc[] classes = root.classes();
        for (int i = 0; i < classes.length; i++) {
            new ClassChecker(classes[i]).check();
        }
        return true;
    }
}
