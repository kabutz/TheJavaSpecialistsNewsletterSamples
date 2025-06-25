package eu.javaspecialists.tjsn.issue079;

import java.lang.reflect.*;

/**
 * This converter only supports Arrays. It supports
 * primitive arrays and multi-dimensional arrays.
 */
public class ArrayConverter extends ToStringConverter {
    public ArrayConverter(ToStringConverter successor) {
        super(successor);
    }

    /**
     * This handler only works for arrays.
     */
    protected boolean isHandler(Object o) {
        return o.getClass().isArray();
    }

    /**
     * We want to append the type of the array and the number of
     * dimensions, e.g. for a three-dimensional array we want to
     * append [][][].  Using += for the postfix String is not
     * ideal, but the most common case will probably be a single
     * dimension array, in which case there would not be any
     * concatenation of Strings.
     */
    protected void appendName(Object o, StringBuffer buf) {
        assert o.getClass().isArray();
        String postfix = "[]";
        Class c = o.getClass().getComponentType();
        while (c.isArray()) {
            postfix += "[]";
            c = c.getComponentType();
        }
        buf.append(Utils.stripPackageName(c));
        buf.append(postfix);
    }

    /**
     * We show the array using the dimensions and the toString()
     * methods of the values.  This method is recursive to handle multi-
     * dimensional arrays.
     */
    protected void appendValues(Object o, StringBuffer buf) {
        assert o.getClass().isArray();
        buf.append('{');
        int length = Array.getLength(o);
        for (int i = 0; i < length; i++) {
            Object value = Array.get(o, i);
            if (value != null && value.getClass().isArray()) {
                appendValues(value, buf);
            } else if (o.getClass().getComponentType().isPrimitive()) {
                buf.append(value);
            } else if (value instanceof String) {
                buf.append('"').append(value).append('"');
            } else {
                ToStringFacade.toString(value, buf);
            }
            if (i < length - 1) {
                buf.append(',');
            }
        }
        buf.append('}');
    }
}
