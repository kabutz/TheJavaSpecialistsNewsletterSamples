package eu.javaspecialists.tjsn.issue272;

import java.lang.reflect.*;

public class Spielverderber {
    public static void main(String... args) {
        Field[] declaredFields = Field.class.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            System.out.println(declaredFields[i]);
        }
    }
}
