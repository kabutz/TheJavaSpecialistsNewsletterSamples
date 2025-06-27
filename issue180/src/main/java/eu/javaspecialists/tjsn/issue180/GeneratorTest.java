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

package eu.javaspecialists.tjsn.issue180;

public class GeneratorTest {
    public static void main(String... args) throws Exception {
        Class testClass = Generator.make(
                null, "WatchThis",
                "" +
                        "package coolthings;\n" +
                        "\n" +
                        "public class WatchThis implements Runnable {\n" +
                        "  public WatchThis() {\n" +
                        "    System.out.println(\"Hey this works!\");\n" +
                        "  }\n" +
                        "\n" +
                        "  public void run() {\n" +
                        "    System.out.println(Thread.currentThread());\n" +
                        "    while(Math.random() < 0.95) {\n" +
                        "      System.out.println(\"Cool stuff!\");\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n"
        );
        Runnable r = (Runnable) testClass.newInstance();
        Class<? extends Runnable> clazz = r.getClass();
        System.out.println("Our class: " + clazz.getName());
        System.out.println("Classloader: " + clazz.getClassLoader());
        Thread t = new Thread(r, "Cool Thread");
        t.start();
    }
}
