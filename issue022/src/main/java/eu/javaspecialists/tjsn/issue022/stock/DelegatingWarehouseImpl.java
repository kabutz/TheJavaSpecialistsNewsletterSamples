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

import eu.javaspecialists.tjsn.issue022.sales.*;

/**
 * Example service that links against sales.Order and introduces
 * a mutual dependency
 */
public class DelegatingWarehouseImpl extends WarehouseImpl {
    /**
     * name of the item at our vendor
     */
    protected String vendorItem;

    /**
     * construct a new delegating warehouse
     */
    public DelegatingWarehouseImpl(String vendorItem, int quantity)
            throws java.rmi.RemoteException {
        super(vendorItem, quantity);
        this.vendorItem = vendorItem;
    }

    /**
     * Overrides the underCapacity reaction to order at vendor
     */
    protected void empty(int underLoad) {
        try {
            new Order(vendorItem, underLoad).reserve();
            quantity += underLoad;
        } catch (Exception ex) {
            throw new IllegalArgumentException(
                    "Could not place order " + ex);
        }
    }
}
