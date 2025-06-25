package eu.javaspecialists.tjsn.issue018;

import java.net.*;

public class Loader2 {
    public static void main(String[] args) throws Exception {
        ClassLoader parent = new URLClassLoader(
                new URL[]{new URL("file:./")}, null);
        ClassLoader a1 = new URLClassLoader(
                new URL[]{new URL("file:a1/")}, parent);
        ClassLoader a2 = new URLClassLoader(
                new URL[]{new URL("file:a2/")}, parent);
        Class c1 = a1.loadClass("A");
        Class c2 = a2.loadClass("A");
        System.out.println(c1.newInstance());
        System.out.println(c2.newInstance());
        System.out.println(
                c1.getSuperclass().equals(c2.getSuperclass()));
        System.out.println(
                Class.forName("Parent").equals(c1.getSuperclass()));
        try {
            Parent p = (Parent) c1.newInstance();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
        System.out.println("We expected to get ClassCastException");
    }
}
