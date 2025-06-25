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

package eu.javaspecialists.tjsn.issue058;

import java.io.*;
import java.rmi.*;
import java.util.*;

public class RMITestClient {
    public static void main(String args[]) throws Exception {
        System.setSecurityManager(new RMISecurityManager());
        RMITestI test = (RMITestI) Naming.lookup(RMITestI.NAME);
        Map values = new HashMap();
        values.put(new Serializable() {
        }, "Today");
        for (int i = 0; i < 13; i++) {
            System.out.print('.');
            System.out.flush();
            values.putAll(test.getValues(values));
        }
    }
}
