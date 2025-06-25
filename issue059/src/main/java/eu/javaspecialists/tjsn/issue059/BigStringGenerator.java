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
