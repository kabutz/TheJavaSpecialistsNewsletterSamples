package eu.javaspecialists.tjsn.issue049;

import com.sun.javadoc.*;

public class FieldChecker extends Checker {
    private final String descriptor;

    public FieldChecker(ClassChecker parent, FieldDoc doc) {
        super(doc);
        descriptor = parent.getDescriptor() + '.' + doc.name();
    }

    public void check() {
        checkComments();
    }

    protected String getDescriptor() {
        return descriptor;
    }
}
