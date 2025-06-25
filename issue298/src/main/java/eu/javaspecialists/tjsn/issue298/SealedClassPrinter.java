package eu.javaspecialists.tjsn.issue298;

import java.lang.constant.*;
import java.lang.reflect.*;

public class SealedClassPrinter {
    private static final int TAB = 4;

    public static void main(String... args) {
        printTree(ConstantDesc.class, 0);
    }

    public static void printTree(Class<?> clazz) {
        printTree(clazz, 0);
    }

    private static void printTree(Class<?> clazz, int level) {
        String indent = " ".repeat(level * TAB);
        String modifier = clazz.isSealed() ? "sealed" :
                Modifier.isFinal(clazz.getModifiers()) ? "final" :
                        "non-sealed";
        String name = clazz.getSimpleName();
        System.out.printf("%s%s %s%n", indent, modifier, name);
        var permittedSubclasses = clazz.getPermittedSubclasses();
        if (permittedSubclasses != null) {
            for (var subclass : permittedSubclasses) {
                printTree(subclass, level + 1);
            }
        }
    }
}
