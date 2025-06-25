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

package eu.javaspecialists.tjsn.issue059;

import java.io.*;

public class ManyParametersGenerator {
    public static void main(String[] args) throws IOException {
        int LENGTH = Integer.parseInt(args[0]);
        System.out.println("Creating java file with " + LENGTH +
                " parameters");
        // First, we generate a class with many parameters in a method

        PrintStream out = CommonFileUtils.createClassFile("ManyParameters");
        out.println("public class ManyParameters {");
        out.println("  public int f(");
        for (int i = 0; i < LENGTH; i++) {
            out.print("      int i" + i);
            if (i == LENGTH - 1) out.println(") {");
            else out.println(",");
        }
        out.println("    int j = 0 ");
        for (int i = 0; i < LENGTH; i++) {
            out.println("      + i" + i);
        }
        out.println("      ;");
        out.println("    System.out.println(j);");
        out.println("    return j;");
        out.println("  }");
        out.println("}");
        out.close();
        // Second, we generate a class that tests our strange class
        out = CommonFileUtils.createClassFile("ManyParametersTest");
        out.println("public class ManyParametersTest {");
        out.println("  public static void main(String[] args) {");
        out.println("    ManyParameters mp = new ManyParameters();");
        out.println("    int j = mp.f(");
        for (int i = 0; i < LENGTH; i++) {
            out.print("      " + (i + 10));
            if (i == LENGTH - 1) out.println(");");
            else out.println(",");
        }
        // we also calculate what "j" should actually be
        int j = 0;
        for (int i = 0; i < LENGTH; i++) {
            j += i + 10;
        }
        out.println("    System.out.println(j);");
        out.println("    System.out.println(" + j + ");");
        out.println("  }");
        out.println("}");
        out.close();
    }
}
