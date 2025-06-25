package eu.javaspecialists.tjsn.issue059;

import java.io.*;

public class BigClassGenerator {
    public static void main(String[] args) throws IOException {
        int LENGTH = Integer.parseInt(args[0]);
        System.out.println("Creating java file with " + LENGTH +
                " data members");
        PrintStream out = CommonFileUtils.createClassFile("BigClass");
        out.println("public class BigClass {");
        for (int i = 0; i < LENGTH; i++) {
            out.println("private int a" + i + ";");
        }
        out.println("}");
        out.close();
    }
}
