package eu.javaspecialists.tjsn.issue014;

import java.lang.reflect.*;

public class Warper {
    private static Field stringValue;

    static {
        // String has a private array called "value"
        // if it does not, find the array and assign it to value
        try {
            stringValue = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException ex) {
            // safety net in case we are running on a VM with a
            // different name for the array.
            Field[] all = String.class.getDeclaredFields();
            for (int i = 0; stringValue == null && i < all.length; i++) {
                if (all[i].getType().equals(char[].class)
                        || all[i].getType().equals(byte[].class)) {
                    stringValue = all[i];
                }
            }
        }
        if (stringValue != null) {
            stringValue.setAccessible(true); // make field public
        }
        System.out.println("stringValue = " + stringValue);
    }

    public Warper() {
        try {
            stringValue.set(
                    "Romeo, Romeo, wherefore art thou oh Romero?",
                    stringValue.get("Stop this romance nonsense, or I'll be sick"));
            stringValue.set("hi there", stringValue.get("cheers !"));
        } catch (IllegalAccessException ex) {} // shhh
    }
}