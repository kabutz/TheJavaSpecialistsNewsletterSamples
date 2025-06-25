package eu.javaspecialists.tjsn.issue304;

import java.io.*;

public class NoStringsPlease {
    public static void main(String... args)
            throws IOException, ClassNotFoundException {
        byte[] bytes = SerializationHelper.serialize("Hello world");
        ObjectInputFilter filter1 = ObjectInputFilter.rejectFilter(
                c -> c == String.class, ObjectInputFilter.Status.ALLOWED
        );
        var result = SerializationHelper.deserialize(filter1, bytes);
        System.out.println(result);

        ObjectInputFilter filter2 =
                ObjectInputFilter.Config.createFilter("maxarray=10");
        result = SerializationHelper.deserialize(filter2, bytes);
        System.out.println(result);
    }
}
