package eu.javaspecialists.tjsn.issue121;

import java.io.*;

/**
 * This class generates a deep class hierarchy, with # levels
 * specified by the first command line parameter.
 */
public class MakeClasses {
    public static void main(String... args) throws IOException {
        String packageName = MakeClasses.class.getPackage().getName() + ".gen";
        String dir = "issue121/src/main/java/" + packageName.replaceAll("\\.", "/");
        File temp = new File(dir);
        temp.mkdirs();
        int levels = Integer.parseInt(args[0]);
        for (int i = 0; i < levels; i++) {
            PrintStream out = new PrintStream(dir + "/Test" + i + ".java");
            String superClass = i == 0 ? "Object" : ("Test" + (i - 1));
            String className = "Test" + i;
            out.println("package " + packageName + ";");
            out.println("public class " + className +
                    " extends " + superClass + " {}");
            out.close();
        }
        PrintStream out = new PrintStream(dir + "/Test.java");
        out.println("package " + packageName + ";");
        out.println("public class Test {");
        out.println("  public static void main(String[] args) {");
        out.println("    Test0 t = " +
                "new Test" + (levels - 1) + "();");
        out.println("    System.out.println(t);");
        out.println("  }");
        out.println("}");
    }
}
