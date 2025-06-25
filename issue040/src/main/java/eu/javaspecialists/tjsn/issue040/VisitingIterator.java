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

package eu.javaspecialists.tjsn.issue040;

import java.lang.reflect.*;
import java.util.*;

public class VisitingIterator {
    /**
     * Ordering methods in "best-fit" order.
     */
    private static final Comparator METHOD_COMPARATOR =
            new Comparator() {
                public int compare(Object o1, Object o2) {
                    Class paramType1 = ((Method) o1).getParameterTypes()[0];
                    Class paramType2 = ((Method) o2).getParameterTypes()[0];
                    return paramType1.isAssignableFrom(paramType2) ? 1 : -1;
                }
            };

    /**
     * Threadsafe version of visit.
     *
     * @param lock the object on which to synchronize
     * @param task is an Object with an execute(...) : void method
     */
    public void visit(Collection c, Object task, Object lock) {
        synchronized (lock) {
            visit(c, task);
        }
    }

    /**
     * @param task is an Object with an execute(...) : void method
     */
    public void visit(Collection c, Object task) {
        TreeSet methods = new TreeSet(METHOD_COMPARATOR);
        Method[] ms = task.getClass().getMethods();
        for (int i = 0; i < ms.length; i++) {
            if (ms[i].getName().equals("execute")
                    && ms[i].getParameterTypes().length == 1) {
                methods.add(ms[i]);
            }
        }
        Iterator it = c.iterator();
        while (it.hasNext()) {
            boolean found = false;
            Object o = it.next();
            Iterator mit = methods.iterator();
            while (!found && mit.hasNext()) {
                Method m = (Method) mit.next();
                if (m.getParameterTypes()[0].isInstance(o)) {
                    try {
                        m.invoke(task, new Object[]{o});
                    } catch (IllegalAccessException ex) {
                        // we were only looking for public methods anyway
                        throw new IllegalStateException();
                    } catch (InvocationTargetException ex) {
                        // The only exceptions we allow to be thrown from
                        // execute are RuntimeException subclases
                        throw (RuntimeException) ex.getTargetException();
                    }
                    found = true;
                }
            }
            if (!found)
                throw new IllegalArgumentException(
                        "No handler found for object type " +
                                o.getClass().getName());
        }
    }
}
