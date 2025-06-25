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
