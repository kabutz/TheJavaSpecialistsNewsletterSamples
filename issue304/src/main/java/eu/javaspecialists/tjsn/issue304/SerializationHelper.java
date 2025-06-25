package eu.javaspecialists.tjsn.issue304;

import java.io.*;
import java.util.*;

class SerializationHelper {
    static byte[] serialize(Object... objects) throws IOException {
        var bout = new ByteArrayOutputStream();
        try (var out = new ObjectOutputStream(bout)) {
            for (Object object : objects) {
                out.writeObject(object);
            }
            out.writeObject(null);
        }
        return bout.toByteArray();
    }

    static List<?> deserialize(byte[] bytes)
            throws IOException, ClassNotFoundException {
        return deserialize(null, bytes);
    }

    static List<?> deserialize(ObjectInputFilter filter, byte[] bytes)
            throws IOException, ClassNotFoundException {
        var result = new ArrayList<>();
        try (var in = new ObjectInputStream(
                new ByteArrayInputStream(bytes)
        )) {
            if (filter != null) in.setObjectInputFilter(filter);
            Object o;
            while ((o = in.readObject()) != null) {
                result.add(o);
            }
        } catch (InvalidClassException ex) {
            result.add(ex.toString());
        }
        return List.copyOf(result);
    }
}
