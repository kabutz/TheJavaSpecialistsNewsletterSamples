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

package eu.javaspecialists.tjsn.issue022.server;

import java.io.*;
import java.net.*;

public class Loader {
    /**
     * Demonstration of some classloading issues
     */
    public static void main(String[] args) throws Exception {
        // construct a tiny classloader hierarchy
        ClassLoader stockLoader = new URLClassLoader(
                new URL[]{getStockLocation()});
        ClassLoader salesLoader = new URLClassLoader(
                new URL[]{getSalesLocation()}, stockLoader);
        // load order class
        Class orderC = salesLoader.loadClass("sales.Order");
        System.out.println(orderC + " loaded from " + salesLoader +
                "; defined by " + orderC.getClassLoader());
        // load warehouse class
        Class wHouseC = salesLoader.loadClass("stock.Warehouse");
        System.out.println(wHouseC + " loaded from " + salesLoader +
                "; defined by " + wHouseC.getClassLoader());
        // analyse class links
        System.out.println("loading and linking same " +
                wHouseC.equals(orderC.getDeclaredField("wHouse").getType()));
        System.exit(0);
    }

    /**
     * where the stock classes can be found
     */
    protected static URL getStockLocation() throws IOException {
        return new File("classes/stock/").toURL();
    }

    /**
     * where the sales classes can be found
     */
    protected static URL getSalesLocation() throws IOException {
        return new File("classes/sales/").toURL();
    }
}
