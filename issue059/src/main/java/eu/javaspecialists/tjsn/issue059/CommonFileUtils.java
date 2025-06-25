package eu.javaspecialists.tjsn.issue059;


import java.io.*;

public class CommonFileUtils {
    public static PrintStream createClassFile(String className) throws FileNotFoundException {
        String pathname = "issue059/src/main/java/eu/javaspecialists/tjsn/issue059/gen/";
        File dir = new File(pathname);
        dir.mkdirs();
        PrintStream out = new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream(pathname + className + ".java")));
        System.out.println("Creating java file with class name " + className);
        out.println("package eu.javaspecialists.tjsn.issue059.gen;");
        return out;
    }
}
