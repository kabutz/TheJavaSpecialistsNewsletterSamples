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
