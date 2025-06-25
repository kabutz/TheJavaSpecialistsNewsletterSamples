package eu.javaspecialists.tjsn.issue109;

import java.util.*;

public class ValueBasedEqualityStrategy
        implements EqualityStrategy {
    private ValueSupplier supplier;

    public void setOwner(Object o) {
        if (!(o instanceof ValueSupplier)) {
            throw new IllegalArgumentException();
        }
        this.supplier = (ValueSupplier) o;
    }

    public int hashCode() {
        return Arrays.deepHashCode(supplier.getValues());
    }

    public boolean equals(Object o) {
        if (o == supplier) return true;
        if (!supplier.getClass().isInstance(o)) return false;
        Object[] values1 = supplier.getValues();
        Object[] values2 = ((ValueSupplier) o).getValues();
        return Arrays.deepEquals(values1, values2);
    }

    public interface ValueSupplier {
        Object[] getValues();
    }
}
