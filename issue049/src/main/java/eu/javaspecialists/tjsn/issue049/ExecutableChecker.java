package eu.javaspecialists.tjsn.issue049;

import com.sun.javadoc.*;

import java.util.*;

public abstract class ExecutableChecker extends Checker {
    protected final String descriptor;
    private final ExecutableMemberDoc doc;

    public ExecutableChecker(ClassChecker parentChecker,
                             ExecutableMemberDoc doc) {
        super(doc);
        descriptor = parentChecker.getDescriptor() + '.' +
                doc.name() + doc.flatSignature();
        this.doc = doc;
    }

    protected String getDescriptor() {
        return descriptor;
    }

    public void check() {
        checkComments(); // calls superclass
        checkReturnComments(); // calls subclass
        checkParametersForComments();
        checkExceptionComments();
    }

    public abstract void checkReturnComments();

    private void checkParametersForComments() {
        ParamTag[] tags = doc.paramTags();
        Map tagMap = new HashMap(tags.length);
        for (int i = 0; i < tags.length; i++) {
            if (tagMap.containsKey(tags[i].parameterName()))
                error("parameter \"" + tags[i].parameterName()
                        + "\" has multiple comments");
            else if (!isEmpty(tags[i].parameterComment()))
                tagMap.put(tags[i].parameterName(), tags[i]);
        }
        Parameter[] params = doc.parameters();
        for (int i = 0; i < params.length; i++) {
            if (tagMap.remove(params[i].name()) == null
                    && !params[i].name().equals("this$0")) {
                error("misses comment for parameter \"" +
                        params[i].name() + "\"");
            }
        }
        Iterator it = tagMap.keySet().iterator();
        while (it.hasNext()) {
            error("parameter \"" + it.next() + "\" does not exist");
        }
    }

    private void checkExceptionComments() {
        ThrowsTag[] tags = doc.throwsTags();
        Map tagMap = new HashMap(tags.length);
        for (int i = 0; i < tags.length; i++) {
            if (tagMap.containsKey(tags[i].exceptionName()))
                error("has multiple comments for exception \"" +
                        tags[i].exceptionName() + "\"");
            else if (!isEmpty(tags[i].exceptionComment()))
                tagMap.put(tags[i].exceptionName(), tags[i]);
        }
        ClassDoc[] exceptions = doc.thrownExceptions();
        for (int i = 0; i < exceptions.length; i++) {
            if (tagMap.remove(exceptions[i].name()) == null)
                error("is missing comments for exception \"" +
                        exceptions[i].name() + "\"");
        }
        Iterator it = tagMap.keySet().iterator();
        while (it.hasNext()) {
            error("has unnecessary comment for exception \"" +
                    it.next() + '"');
        }
    }

    protected void foundCommentsForNonExistentReturnValue() {
        error("has unnecessary return comment");
    }
}
