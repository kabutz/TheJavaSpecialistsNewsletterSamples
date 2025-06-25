/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
