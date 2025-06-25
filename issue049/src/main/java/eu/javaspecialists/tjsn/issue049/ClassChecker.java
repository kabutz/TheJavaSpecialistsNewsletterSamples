package eu.javaspecialists.tjsn.issue049;

import com.sun.javadoc.*;

/**
 * Check whether the class has comments
 */
public class ClassChecker extends Checker {
    private final ClassDoc doc;

    public ClassChecker(ClassDoc doc) {
        super(doc);
        this.doc = doc;
    }

    protected String getDescriptor() {
        return doc.qualifiedName();
    }

    public void check() {
        checkComments(); // calls superclass
        checkConstructors();
        checkMethods();
        checkFields();
    }

    private void checkConstructors() {
        ConstructorDoc[] constructors = doc.constructors();
        for (int i = 0; i < constructors.length; i++) {
            new ConstructorChecker(this, constructors[i]).check();
        }
    }

    private void checkMethods() {
        MethodDoc[] methods = doc.methods();
        for (int i = 0; i < methods.length; i++) {
            new MethodChecker(this, methods[i]).check();
        }
    }

    private void checkFields() {
        FieldDoc[] fields = doc.fields();
        for (int i = 0; i < fields.length; i++) {
            new FieldChecker(this, fields[i]).check();
        }
    }
}
