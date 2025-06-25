package eu.javaspecialists.tjsn.issue254;

import java.lang.reflect.*;

public class ReflectionTest {
    public static void main(String... args)
            throws NoSuchMethodException {
        System.out.println(A.class.getMethod("foo"));
        System.out.println(B.class.getMethod("foo"));
        System.out.println(C.class.getMethod("foo"));

        System.out.println("All methods in class C:");

        for (Method method : C.class.getMethods()) {
            System.out.println(method);
        }
    }
}
