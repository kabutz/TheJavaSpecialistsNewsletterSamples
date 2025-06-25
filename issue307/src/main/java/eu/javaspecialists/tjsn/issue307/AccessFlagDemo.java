package eu.javaspecialists.tjsn.issue307;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class AccessFlagDemo {
    public static void main(String... args) {
        Arrays.stream(MethodsDemo.class.getDeclaredMethods())
                .sorted(Comparator.comparing(Method::getName))
                .forEach(method -> System.out.println("""
                        %s:
                            Method: %s
                            Modifiers: %s
                            Modifiers Hex: %s
                            AccessFlags: %s
                        """.formatted(method.getName(),
                        method,
                        Modifier.toString(method.getModifiers()),
                        hexValues(method.getModifiers()),
                        method.accessFlags())));
    }

    private static String hexValues(int modifiers) {
        int bit = 1;
        List<Integer> values = new ArrayList<>();
        while (modifiers != 0) {
            if ((modifiers & bit) != 0) values.add(bit);
            modifiers = modifiers & ~bit;
            bit <<= 1;
        }
        return values.stream()
                .map(val -> String.format("0x%04x", val))
                .collect(Collectors.joining(" ", "[", "]"));
    }
}
