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

package eu.javaspecialists.tjsn.issue022;

import java.rmi.*;
import java.util.*;

/**
 * Example service-publishing application
 */
public class Main {
    /**
     * the services provided by this application
     */
    protected static Collection services = new Vector();

    /**
     * create and export services
     */
    public static void main(String[] args) throws Exception {
        System.setSecurityManager(new RMISecurityManager());
        for (int count = 0; count < args.length; count++) {
            services.add(args[count]);
            // use context classloader to resolve class names
            Naming.rebind("//localhost/" + args[count++],
                    (Remote) Thread.currentThread().getContextClassLoader().
                            loadClass(args[count++]).getConstructor(
                                    new Class[]{String.class, int.class}).newInstance(
                                    new Object[]{args[count++],
                                            new Integer(args[count])}));
        }
    }

    /**
     * tearDown services means unPublish
     */
    public static void stop() {
        Iterator allServices = services.iterator();
        while (allServices.hasNext()) {
            try {
                Naming.unbind("//localhost/" + allServices.next());
            } catch (Exception e) {}
        }
    }
}