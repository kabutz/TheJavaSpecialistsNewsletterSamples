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
