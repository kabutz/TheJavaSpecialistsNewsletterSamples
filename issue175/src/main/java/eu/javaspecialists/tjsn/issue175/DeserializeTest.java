package eu.javaspecialists.tjsn.issue175;

import java.io.*;

public class DeserializeTest {
    public static void main(String... args)
            throws IOException, ClassNotFoundException {
        MySerializable ms = new MySerializable();

        // writing object to byte[]
        System.out.println("writing ms");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(ms);
        oos.close();
        byte[] objectInBinaryForm = baos.toByteArray();

        // reading object from byte[]
        System.out.println("reading ms2");
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(objectInBinaryForm)
        );
        MySerializable ms2 = (MySerializable) ois.readObject();
        System.out.println("ms == ms2 = " + (ms == ms2));
        System.out.println("ms = " + ms);
        System.out.println("ms2 = " + ms2);
    }
}
