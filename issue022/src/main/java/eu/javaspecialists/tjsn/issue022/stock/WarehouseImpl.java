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