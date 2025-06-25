package eu.javaspecialists.tjsn.issue254;

import java.io.*;

public class ClassGenerator {
    public static void main(String... args) throws IOException {
        for (int methods = 4; methods < 65536; methods *= 5) {
            PrintStream out = new PrintStream(
                    new FileOutputStream("Methods" + methods + ".java"));
            out.println("public class Methods" + methods + " {");
            for (int i = 0; i < methods; i++) {
                out.println("  public void foo" + i + "() {}");
            }
            out.println("}");
            out.close();
        }
    }
}
