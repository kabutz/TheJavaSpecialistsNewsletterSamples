package eu.javaspecialists.tjsn.issue059;

import java.io.*;

public class ClassGenerator {
    public static void main(String[] args) throws IOException {
        int NUMBER_OF_CLASSES = Integer.parseInt(args[0]);
        for (int i = 0; i < NUMBER_OF_CLASSES; i++) {
            PrintStream out = CommonFileUtils.createClassFile("A" + i);
            out.println("public class A" + i + " { private A" +
                    ((i + 1) % NUMBER_OF_CLASSES) + " other; }");
            out.close();
        }
    }
}
