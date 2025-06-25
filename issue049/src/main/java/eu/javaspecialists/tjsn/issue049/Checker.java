package eu.javaspecialists.tjsn.issue049;

import com.sun.javadoc.*;

/**
 * Abstract superclass for checking a code component.
 */
public abstract class Checker {
    private final Doc doc;

    public Checker(Doc doc) {
        this.doc = doc;
    }

    public abstract void check();

    protected abstract String getDescriptor();

    protected final boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public void checkComments() {
        if (isEmpty(doc.commentText()))
            error("misses comment");
    }

    protected void error(String msg) {
        System.err.println(getDescriptor() + ' ' + msg);
    }
}
