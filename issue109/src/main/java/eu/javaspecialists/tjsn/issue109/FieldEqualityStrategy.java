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
