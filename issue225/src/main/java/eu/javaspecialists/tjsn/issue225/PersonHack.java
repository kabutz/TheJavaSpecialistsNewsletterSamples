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

package eu.javaspecialists.tjsn.issue225;

import java.io.*;

public class PersonHack {
    public static void main(String... args)
            throws IOException, ClassNotFoundException {
        Person heinz = new Person("Heinz", "Kabutz", 43);
        // convert to a byte[]
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        oout.writeObject(heinz);
        oout.close();

        byte[] bytes = bout.toByteArray();
        int index = -1;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == 0 && bytes[i + 1] == 0 && bytes[i + 2] == 0 &&
                    bytes[i + 3] == 43) {
                if (index != -1)
                    throw new IllegalStateException("Duplicate index");
                index = i;
            }
        }
        int newAge = -50;
        setAge(bytes, index, newAge);

        ObjectInputStream oin = new ObjectInputStream(
                new ByteArrayInputStream(bytes)
        );
        Person youngerHeinz = (Person) oin.readObject();
        System.out.println("heinz = " + heinz);
        System.out.println("younger heinz = " + youngerHeinz);
    }

    private static void setAge(byte[] bytes, int index, int age) {
        // in the object output stream, ints are encoded Big Endian
        bytes[index] = (byte) (age >>> 24);
        bytes[index + 1] = (byte) (age >>> 16);
        bytes[index + 2] = (byte) (age >>> 8);
        bytes[index + 3] = (byte) age;
    }
}
