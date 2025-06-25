package eu.javaspecialists.tjsn.issue117;

public class Exec {
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
            cls.getMethod(method, null).invoke(target, null);
        } catch (Exception x) {
            System.out.println(x);
        }
    }
}
