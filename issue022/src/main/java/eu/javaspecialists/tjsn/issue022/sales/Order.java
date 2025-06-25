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

package eu.javaspecialists.tjsn.issue022.sales;


import eu.javaspecialists.tjsn.issue022.stock.*;

import java.rmi.*;

/**
 * Class that links in another jar
 */
public class Order {
    /**
     * the stock.Warehouse which hosts our items
     */
    private final Warehouse wHouse;
    /**
     * how much do we need?
     */
    private final int amount;
    /**
     * quantize reservations
     */
    private static int bunch = 5;

    /**
     * constructs a new order
     */
    public Order(String itemName, int quantity)
            throws java.net.MalformedURLException, NotBoundException,
            RemoteException {
        // look into the rmi registry to locate the warehouse
        wHouse = (Warehouse) Naming.lookup("//localhost/" + itemName);
        this.amount = quantity;
    }

    /**
     * method that delegates reservation to warehouse
     */
    public void reserve() throws RemoteException {
        for (int count = 0; count < amount; count += bunch)
            wHouse.reserve(bunch);
    }
}
