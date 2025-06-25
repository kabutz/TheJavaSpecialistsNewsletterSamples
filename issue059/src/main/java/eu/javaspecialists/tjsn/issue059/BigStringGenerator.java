package eu.javaspecialists.tjsn.issue059;

import java.io.*;

public class BigStringGenerator {
    public static void main(String[] args) throws IOException {
        int LENGTH = Integer.parseInt(args[0]);
        System.out.println("Creating java file with string of length " + LENGTH);
        PrintStream out = CommonFileUtils.createClassFile("BigString");
        out.println("public class BigString {");
        out.print("  public String big = ");
        out.print("\"");
        for (int i = 0; i < LENGTH; i++) {
            out.print((char) ((i % 26) + 'A'));
        }
        out.print("\"");
        out.println(";");
        out.println("}");
        out.close();
        out = CommonFileUtils.createClassFile("BigStringTest");
        out.println("public class BigStringTest {");
        out.println("  public static void main(String[] args) {");
        out.println("    try {");
        out.println("      BigString bs = new BigString();");
        out.println("      System.out.println(bs.big.length());");
        out.println("    } catch(Throwable t) { System.err.println(t); }");
        out.println("  }");
        out.println("}");
        out.close();
    }
}
