package eu.javaspecialists.tjsn.issue315;

import java.lang.reflect.*;
import java.util.*;
import java.util.random.*;
import java.util.stream.*;

public class ShowGenerators {
    public static void main(String... args) {
        Stream.of(RandomGeneratorFactory.class.getMethods())
                .filter(method -> method.getName().startsWith("is"))
                .sorted(Comparator.comparing(Method::getName))
                .forEach(ShowGenerators::printAttributeDetails);
    }

    private static void printAttributeDetails(Method method) {
        System.out.println(method.getName() + ":");
        RandomGeneratorFactory.all()
                .filter(factory -> {
                    try {
                        return (boolean) method.invoke(factory);
                    } catch (ReflectiveOperationException e) {
                        throw new IllegalStateException(e);
                    }
                })
                .map(RandomGeneratorFactory::create)
                .map(Object::getClass)
                .map(Class::getSimpleName)
                .sorted()
                .map("\t%s"::formatted)
                .forEach(System.out::println);
    }
}
