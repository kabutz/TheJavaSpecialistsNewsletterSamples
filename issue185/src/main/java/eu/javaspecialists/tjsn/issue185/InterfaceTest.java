package eu.javaspecialists.tjsn.issue185;

import java.lang.reflect.*;

public class InterfaceTest {
    public static void main(String... args) {
        showMethods(PackageInterface.class);
        showMethods(PublicInterface.class);
    }

    private static void showMethods(Class<?> clazz) {
        System.out.println("Methods for " + clazz.getName());
        for (Method m : clazz.getMethods()) {
            String modifier = Modifier.isPublic(m.getModifiers()) ? "public" : "package";
            System.out.printf("%s %s%n", modifier, m.getName());
        }
    }
}
