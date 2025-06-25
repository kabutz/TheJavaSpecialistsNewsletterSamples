package eu.javaspecialists.tjsn.issue049;

import com.sun.javadoc.*;

public class ConstructorChecker extends ExecutableChecker {
    private final ConstructorDoc doc;

    public ConstructorChecker(ClassChecker parent,
                              ConstructorDoc doc) {
        super(parent, doc);
        this.doc = doc;
    }

    public void checkReturnComments() {
        if (doc.tags("return").length > 0)
            foundCommentsForNonExistentReturnValue();
    }
}
