package eu.javaspecialists.tjsn.issue109;

import java.lang.reflect.*;
import java.util.*;

public class FieldEqualityStrategy implements EqualityStrategy {
    private Object obj;
    private Field[] fields;

    public void setOwner(Object obj) {
        this.obj = obj;
        // we want to filter out the strategy field!
        List fields = new ArrayList();
        Field[] tempFields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < tempFields.length; i++) {
            Field field = tempFields[i];
            if (!field.getType().isAssignableFrom(getClass())) {
                field.setAccessible(true);
                fields.add(field);
            }
        }
        this.fields = new Field[fields.size()];
        fields.toArray(this.fields);
    }

    public int hashCode() {
        try {
            int hashCode = 0;
            for (int i = 0; i < fields.length; i++) {
                Object o = fields[i].get(obj);
                // you might need to make special provisions for arrays
                hashCode = 29 * hashCode + (o == null ? 0 : o.hashCode());
            }
            return hashCode;
        } catch (IllegalAccessException e) {
            throw new SecurityException(e);
        }
    }

    public boolean equals(Object o) {
        if (o == obj) return true;
        if (!obj.getClass().isInstance(o)) return false;
        try {
            for (int i = 0; i < fields.length; i++) {
                Object val1 = fields[i].get(obj);
                Object val2 = fields[i].get(o);
                // you might need to make special provisions for arrays
                if (val1 != null ? !val1.equals(val2) : val2 != null)
                    return false;
            }
        } catch (IllegalAccessException e) {
            throw new SecurityException(e);
        }
        return true;
    }
}
