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

package eu.javaspecialists.tjsn.issue097;

import org.dom4j.*;

import java.lang.reflect.*;

public class XmlConstructor {
    public static void constructFromXml(Object obj, Element elem) {
        constructFromXml(obj, elem, false);
    }

    /**
     * Set object's fields from values of XML declarations, using
     * "@FromXml" annotation
     * <p>
     * Scans all fields in an object for a annotated elements, and
     * initialize them, according to the fields type.
     *
     * @param useSuper If super class is to be processed
     * @param o        The object to scan for fields
     * @param element  The element whose attributes are the data
     *                 sources.
     */
    public static void constructFromXml(Object o, Element element,
                                        boolean useSuper) {
        if (element == null) {
            return;
        }
        Class aClass = getAppropriateClass(o, useSuper);

        for (Field field : aClass.getDeclaredFields()) {
            FromXml fromXml = field.getAnnotation(FromXml.class);
            if (fromXml != null) {
                field.setAccessible(true);
                handleAnnotatedField(fromXml, element, field, o);
            }
        }
    }

    private static void handleAnnotatedField(FromXml fromXml,
                                             Element element,
                                             Field field, Object o) {
        String value = getValue(fromXml.xPath(), element, field, fromXml);
        if (!isEmpty(value)) {
            if (fromXml.trim()) {
                value = value.trim();
            }
            setField(field, o, value);
        }
    }

    private static String getValue(String xPath, Element element,
                                   Field field, FromXml fromXml) {
        String value = null;
        if (!isEmpty(xPath)) {
            Node node = element.selectSingleNode(xPath);
            if (node != null) {
                value = node.getText();
            }
        } else {
            // If no xPath, use name
            // Get value of matching attribute
            value = element.attributeValue(field.getName());
        }

        if (value == null) {
            //Use default
            value = fromXml.dflt();
        }
        return value;
    }

    private static void setField(Field field, Object o, String value) {
        Class type = field.getType();
        try {
            // Now initialize field according to type
            if (type.equals(int.class)) {
                field.setInt(o, asHexInt(value));
            } else if (type.equals(String.class)) {
                field.set(o, value.intern());
            } else if (type.equals(double.class)) {
                field.setDouble(o, Double.parseDouble(value));
            } else if (type.equals(boolean.class)) {
                field.setBoolean(o, asBoolean(value));
            } else if (type.equals(char.class)) {
                field.setChar(o, value.charAt(0));
            }
        } catch (IllegalAccessException ex) {
            // this should not happen, since we are setting the access
            // to true
            throw new RuntimeException(ex);
        }
    }

    private static Class getAppropriateClass(Object o, boolean useSuper) {
        Class aClass = o.getClass();
        if (useSuper) {
            aClass = aClass.getSuperclass();
        }
        return aClass;
    }

    private static boolean isEmpty(String test) {
        return test == null || test.length() == 0;
    }

    /**
     * Use hex conversion if string starts with "0x", else decimal
     * conversion.
     */
    private static int asHexInt(String value) {
        if (value.toLowerCase().startsWith("0x")) {
            return Integer.parseInt(value.substring(2), 16);
        }
        return Integer.parseInt(value);
    }

    private static boolean asBoolean(String option) {
        if (!isEmpty(option)) {
            String opt = option.toLowerCase();
            return "yes".equals(opt) || "on".equals(opt)
                    || "true".equals(opt) || "1".equals(opt);
        }
        return false;
    }
}
