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

package eu.javaspecialists.tjsn.issue003;

import java.io.*;

public class TeeDemo1 {
    // ... the main class that starts up the application server
    public static void main(String[] args) throws IOException {
        PrintStream out1 = System.out;
        OutputStream out2 = new BufferedOutputStream(
                new FileOutputStream("LOG"));
        TeeOutputStream newOut = new TeeOutputStream(out1, out2);
        System.setOut(new PrintStream(newOut, true));
        out1 = System.err;
        newOut = new TeeOutputStream(out1, out2);
        System.setErr(new PrintStream(newOut, true));
        // ... some more code...
    }
}