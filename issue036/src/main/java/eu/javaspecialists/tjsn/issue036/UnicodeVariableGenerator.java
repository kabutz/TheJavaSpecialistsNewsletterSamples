package eu.javaspecialists.tjsn.issue036;

import java.io.*;

public class UnicodeVariableGenerator {
    public static void generateMathsSymbols() throws IOException {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream("MathsSymbols.java"), "UTF-16BE"));
        out.println("public interface MathsSymbols {");
        out.print("  public static final double ");
        out.print((char) 960);
        out.println(" = 3.14159265358979323846;");
        out.print("  public static final double ");
        out.print((char) 949);
        out.println(" = 2.7182818284590452354;");
        out.println("}");
        out.close();
    }

    public static void generateMathsSymbolsTest() throws IOException {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream("MathsSymbolsTest.java"), "UTF-16BE"));
        out.println("public class MathsSymbolsTest implements MathsSymbols {");
        out.println("  public static void main(String args[]) {");
        out.println("    System.out.println(\"The value of PI is: \" + \u03C0);");
        out.println("    System.out.println(\"The value of E is: \" + \u03B5);");
        out.println("  }");
        out.println("}");
        out.close();
    }

    public static void main(String[] args) throws IOException {
        generateMathsSymbols();
        generateMathsSymbolsTest();
    }
}
