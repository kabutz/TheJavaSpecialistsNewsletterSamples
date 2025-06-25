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

package eu.javaspecialists.tjsn.issue022.stock;


import java.rmi.server.*;

/**
 * Example implementation of a remote service
 */
public class WarehouseImpl extends UnicastRemoteObject
        implements Warehouse {
    /**
     * number of stored items
     */
    protected int quantity;

    /**
     * constructs warehouse
     */
    public WarehouseImpl(String itemName, int quantity)
            throws java.rmi.RemoteException {
        this.quantity = quantity;
    }

    /**
     * reserves items
     */
    public void reserve(int amount) {
        System.out.println(this + " is about to reserve " +
                amount + " items.");
        if (quantity < amount) empty(amount - quantity);
        quantity -= amount;
    }

    /**
     * what to do if the warehouse is empty
     */
    protected void empty(int underLoad) {
        throw new IllegalArgumentException("warehouse empty");
    }
}