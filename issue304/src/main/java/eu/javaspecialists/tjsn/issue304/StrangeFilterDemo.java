package eu.javaspecialists.tjsn.issue304;

import java.io.*;
import java.util.stream.*;

public class StrangeFilterDemo {
    public static void main(String... args)
            throws IOException, ClassNotFoundException {
        byte[] bytes = SerializationHelper.serialize((Object)
                Stream.of("Hello", "world").toArray(String[]::new));
        ObjectInputFilter filter1 =
                ObjectInputFilter.Config.createFilter("!java.lang.String");
        var result = SerializationHelper.deserialize(filter1, bytes);
        System.out.println(result);
    }
}
