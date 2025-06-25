package eu.javaspecialists.tjsn.issue018;

//: Loader.java

import java.net.*;

public class Loader {
    public static void main(String[] args) throws Exception {
        ClassLoader a1 = new URLClassLoader(
                new URL[]{new URL("file:a1/")}, null);
        ClassLoader a2 = new URLClassLoader(
                new URL[]{new URL("file:a2/")}, null);
        Class c1 = a1.loadClass("A");
        Class c2 = a2.loadClass("A");
        System.out.println("c1.toString(): " + c1);
        System.out.println("c2.toString(): " + c2);
        System.out.println("c1.equals(c2): " + c1.equals(c2));
        System.out.println("c1.newInstance(): " + c1.newInstance());
        System.out.println("c2.newInstance(): " + c2.newInstance());
    }
}
