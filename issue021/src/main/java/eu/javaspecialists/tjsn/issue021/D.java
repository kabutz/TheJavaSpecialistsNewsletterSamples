package eu.javaspecialists.tjsn.issue021;

import java.lang.reflect.*;

public class D {
    public static void main(String[] args) throws Exception {
        Method f = A.class.getDeclaredMethod("f", new Class[0]);
        f.invoke(new A(), new Object[0]);
        f.invoke((A) new B(), new Object[0]);
        f.invoke(new B(), new Object[0]);
    }
}