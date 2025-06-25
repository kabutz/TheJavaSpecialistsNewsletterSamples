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

package eu.javaspecialists.tjsn.issue088;

import java.io.*;
import java.net.*;

public class Sender {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Socket s = new Socket("localhost", 7000);
        ObjectOutputStream oos = new ObjectOutputStream(
                s.getOutputStream());
        Person p = new Person("Heinz", "Kabutz", 0);
        for (int age = 0; age < 1500 * 1000; age++) {
            p.setAge(age);
            oos.writeObject(p);
        }
        long end = System.currentTimeMillis();
        System.out.println("That took " + (end - start) + "ms");
    }
}