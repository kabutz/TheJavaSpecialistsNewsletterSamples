package eu.javaspecialists.tjsn.issue014;

import java.lang.reflect.*;

public class BlaTest {
    private static Field c1;
    private static Field c2;

    static {
        try {
            c1 = Bla.class.getDeclaredField("c1");
            c1.setAccessible(true);
            c2 = Bla.class.getDeclaredField("c2");
            c2.setAccessible(true);
        } catch (NoSuchFieldException ex) {}
    }

    public static void main(String[] args) {
        Bla bla = new Bla();
        try {
            c1.set(bla, "mutatedc1".toCharArray());
            c2.set(bla, "mutatedc2".toCharArray());
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        System.out.println(bla);
    }
}