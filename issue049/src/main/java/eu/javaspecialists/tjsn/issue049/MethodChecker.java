package eu.javaspecialists.tjsn.issue049;

import com.sun.javadoc.*;

public class MethodChecker extends ExecutableChecker {
    private final MethodDoc doc;

    public MethodChecker(ClassChecker parent, MethodDoc doc) {
        super(parent, doc);
        this.doc = doc;
    }

    public void checkReturnComments() {
        Tag[] tags = doc.tags("return");
        if ("void".equals(doc.returnType().qualifiedTypeName())) {
            if (tags.length != 0) {
                foundCommentsForNonExistentReturnValue();
            }
        } else if (tags.length == 0 || isEmpty(tags[0].text())) {
            error("missing return comment");
        } else if (tags.length > 1) {
            error("has multiple return comments");
        }
    }
}
