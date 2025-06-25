package eu.javaspecialists.tjsn.issue035;

import com.sun.javadoc.*;

/**
 * This class is used as a Doclet and will find any non-private
 * data members in your classes.
 */
public class CodeChecker {
    /**
     * This is the entry point for the doclet engine into your
     * class.
     */
    public static boolean start(RootDoc root) {
        System.out.println("Non-private data members:");
        checkClasses(root.classes());
        return true;
    }

    /**
     * We will call the checkClasses() method recursively so that
     * we can also check the inner classes (for what it's worth).
     */
    private static void checkClasses(ClassDoc[] cds) {
        for (int i = 0; i < cds.length; i++) {
            checkDataMembersArePrivate(cds[i]);
        }
    }

    /**
     * This method prints out any data members that are not private
     * together with their access.  If the field is package access
     * we print out no modifiers.
     */
    private static void checkDataMembersArePrivate(ClassDoc cd) {
        System.out.println(cd.modifiers() + " " + cd.qualifiedName() + ":");
        FieldDoc[] fields = cd.fields();
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].isPrivate()) {
                System.out.print("\t");
                if (!fields[i].isPackagePrivate())
                    System.out.print(fields[i].modifiers() + " ");
                System.out.println(fields[i].type() + " " + fields[i].name());
            }
        }
        checkClasses(cd.innerClasses());
    }
}