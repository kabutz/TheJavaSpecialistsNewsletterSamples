package eu.javaspecialists.tjsn.issue096;

import java.lang.reflect.*;

public class FinalStaticFieldChange {
    /**
     * Static fields of type String or primitive would get inlined
     */
    private static final String stringValue = "original value";
    private static final Object objValue = stringValue;

    private static void changeStaticField(String name)
            throws NoSuchFieldException, IllegalAccessException {
        Field statFinField = FinalStaticFieldChange.class.getDeclaredField(name);
        statFinField.setAccessible(true);
        statFinField.set(null, "new Value");
    }

    public static void main(String... args) throws Exception {
        changeStaticField("stringValue");
        changeStaticField("objValue");
        System.out.println("stringValue = " + stringValue);
        System.out.println("objValue = " + objValue);
        System.out.println();
    }
}
