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

package eu.javaspecialists.tjsn.issue079;

import java.lang.reflect.*;
import java.util.*;

public class ObjectConverter extends ToStringConverter {
    public ObjectConverter(ToStringConverter successor) {
        super(successor);
    }

    /**
     * This is the end-point of the chain.  If we get here, we
     * use this handler.
     */
    protected boolean isHandler(Object o) {
        return true;
    }

    /**
     * We specify a different separator for between name and
     * values to make the output look nicer.
     */
    protected char getSeparator() {
        return ':';
    }

    /**
     * For the name of the class, we strip off the package name.
     */
    protected void appendName(Object o, StringBuffer buf) {
        buf.append(Utils.stripPackageName(o.getClass()));
    }

    /**
     * The values are a bit more complicated.  We first have to
     * find all fields.  To find all private fields, we have to
     * recurse up the hierarchy of the object.  We then go through
     * all the fields and append the name and the value.  Unless
     * we have reached the end, we append ", ".
     */
    protected void appendValues(Object o, StringBuffer buf) {
        Iterator it = findAllFields(o);
        while (it.hasNext()) {
            Field f = (Field) it.next();
            appendFieldName(f, o, buf);
            buf.append('=');
            appendFieldValue(f, o, buf);
            if (it.hasNext()) {
                buf.append(", ");
            }
        }
    }

    /**
     * If the field's class is not the object's class (i.e. the
     * field is declared in a superclass) then we print out the
     * superclass' name together with the field name.
     */
    private void appendFieldName(Field f, Object o, StringBuffer buf) {
        if (f.getDeclaringClass() != o.getClass()) {
            buf.append(Utils.stripPackageName(f.getDeclaringClass()));
            buf.append('.');
        }
        buf.append(f.getName());
    }

    /**
     * We set the field to be "accessible", i.e. public.  If the
     * type of the field is primitive, we simply append the value;
     * otherwise we recursively print the value using our
     * ToStringFacade.
     */
    private void appendFieldValue(Field f, Object o, StringBuffer buf) {
        try {
            f.setAccessible(true);
            Object value = f.get(o);
            if (f.getType().isPrimitive()) {
                buf.append(value);
            } else {
                ToStringFacade.toString(value, buf);
            }
        } catch (IllegalAccessException e) {
            assert false : "We have already set it accessible!";
        }
    }

    /**
     * Find all fields of an object, whether private or public.
     * We also look at the fields in super classes.
     */
    private Iterator findAllFields(Object o) {
        Collection result = new LinkedList();
        Class c = o.getClass();
        while (c != null) {
            Field[] f = c.getDeclaredFields();
            for (int i = 0; i < f.length; i++) {
                if (!Modifier.isStatic(f[i].getModifiers())) {
                    result.add(f[i]);
                }
            }
            c = c.getSuperclass();
        }
        return result.iterator();
    }
}
