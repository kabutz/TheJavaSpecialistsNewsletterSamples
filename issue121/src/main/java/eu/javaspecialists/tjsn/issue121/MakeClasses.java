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
