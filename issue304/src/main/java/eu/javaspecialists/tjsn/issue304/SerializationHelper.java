/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
