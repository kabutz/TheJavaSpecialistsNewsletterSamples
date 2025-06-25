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

package eu.javaspecialists.tjsn.issue095;

import java.beans.*;
import java.io.*;
import java.util.*;

public class Test {
    public static void main(String... args) throws Exception {
        String filename = "test.xml";
        Properties defaults = new Properties();
        defaults.setProperty("name", "Heinz");
        defaults.setProperty("yahooid", "heinzkabutz");
        defaults.setProperty("age", "32");
        Configuration cfg = new XMLFileConfiguration(defaults, filename, 300);
        cfg.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Property " + evt.getPropertyName() +
                        " has now changed from <" + evt.getOldValue() +
                        "> to <" + evt.getNewValue() + ">");
            }
        });
        Thread.sleep(1000);
        defaults.setProperty("age", "33");
        FileOutputStream fos = new FileOutputStream(filename);
        defaults.storeToXML(fos, "");
        fos.close();

        Thread.sleep(1000);
        defaults.setProperty("age", "32");
        fos = new FileOutputStream(filename);
        defaults.storeToXML(fos, "");
        fos.close();

        Thread.sleep(1000);
        defaults.setProperty("age", "2 ^ 4");
        fos = new FileOutputStream(filename);
        defaults.storeToXML(fos, "");
        fos.close();

        Thread.sleep(1000);
    }
}
