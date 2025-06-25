package eu.javaspecialists.tjsn.issue183;

import java.io.*;
import java.util.*;

public class ListWritingSize {
    public static void main(String... args) throws IOException {
        test(new LinkedList<String>());
        test(new ArrayList<String>());
    }

    public static void test(List<String> list) throws IOException {
        for (int i = 0; i < 10; i++) {
            list.add("hello world");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(list);
        out.close();
        System.out.println(list.getClass().getSimpleName() +
                " used " + baos.toByteArray().length + " bytes");
    }
}
