package eu.javaspecialists.tjsn.issue298;

import java.lang.constant.*;
import java.util.stream.*;

public class StreamOfConstantDesc {
    public static void main(String... args) {
        Stream<Class<?>> stream = StreamSupport.stream(
                new ClassSpliterator(ConstantDesc.class), false
        );
        stream.forEach(System.out::println);
    }
}
