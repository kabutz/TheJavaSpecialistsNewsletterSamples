package eu.javaspecialists.tjsn.issue117;

import java.lang.reflect.*;

public class Exec2 {
    public static void run(Greeter target) {
        System.out.println();

        System.out.print("method call> ");
        target.hello();

        System.out.print("base class > ");
        run(target, Greeter.class, "hello");

        System.out.print("obj class  > ");
        run(target, target.getClass(), "hello");
    }

    // this calls the method using reflection
    static void run(Greeter target, Class cls, String method) {
        try {
            findHighestMethod(cls, method).invoke(target, null);
        } catch (Exception x) {
            System.out.println(x);
        }
    }

    // recurse up hierarchy, looking for highest method
    private static Method findHighestMethod(Class cls,
                                            String method) {
        if (cls.getSuperclass() != null) {
            Method parentMethod = findHighestMethod(
                    cls.getSuperclass(), method);
            if (parentMethod != null) return parentMethod;
        }
        Method[] methods = cls.getMethods();
        for (int i = 0; i < methods.length; i++) {
            // we ignore parameter types for now - you need to add this
            if (methods[i].getName().equals(method)) {
                return methods[i];
            }
        }
        return null;
    }
}
