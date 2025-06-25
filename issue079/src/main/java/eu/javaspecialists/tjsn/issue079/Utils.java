package eu.javaspecialists.tjsn.issue079;

public class Utils {
    /**
     * We only want to see the class name, without the package.
     */
    public static String stripPackageName(Class c) {
        return c.getName().replaceAll(".*\\.", "").replace('$', '.');
    }
}
