package eu.javaspecialists.tjsn.issue225.take1;

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
