package eu.javaspecialists.tjsn.issue272;

import java.lang.invoke.*;
import java.lang.reflect.*;

// run with --add-opens java.base/java.lang.reflect=ALL-UNNAMED
public class HiddenFieldsRevealed {
    private static final Object greeting = "Hello world";

    public static void main(String... args)
            throws ReflectiveOperationException {
        VarHandle vh = MethodHandles.privateLookupIn(
                Field.class, MethodHandles.lookup()
        ).findVarHandle(Field.class, "modifiers", int.class);
        Field greetingField = HiddenFieldsRevealed.class
                .getDeclaredField("greeting");
        System.out.println("isFinal=" +
                Modifier.isFinal(greetingField.getModifiers()));
        vh.set(greetingField, 0);
        System.out.println("isFinal=" +
                Modifier.isFinal(greetingField.getModifiers()));
    }
}
