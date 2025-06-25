package eu.javaspecialists.tjsn.issue237;

//import sun.misc.*; // prior to Java 9, use this

import jdk.internal.misc.*;

public class StringUnsafeTest {
    private static String s;

    public static void main(String... args) {
        char[] chars = "hello world".toCharArray();
        JavaLangAccess javaLang = SharedSecrets.getJavaLangAccess();
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100 * 1000 * 1000; i++) {
            s = javaLang.newStringUnsafe(chars);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }
}
