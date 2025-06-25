package eu.javaspecialists.tjsn.issue298;

import java.lang.constant.*;
import java.util.*;

public class PermittedSubclassesOfConstantDesc {
    public static void main(String... args) {
        Arrays.stream(ConstantDesc.class.getPermittedSubclasses())
                .forEach(System.out::println);
    }
}
