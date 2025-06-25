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

package eu.javaspecialists.tjsn.issue009;

import java.lang.reflect.*;

public class DepthFirst {
    public static class Top {
        private Method getPolymorphicMethod(Object param) {
            try {
                Class cl = getClass();  // the bottom-most class
                // we start at the bottom and work our way up
                Class[] paramTypes = {param.getClass()};
                while (!cl.equals(Top.class)) {
                    try {
                        // this way we find the actual method
                        return cl.getDeclaredMethod("f", paramTypes);
                    } catch (NoSuchMethodException ex) {}
                    cl = cl.getSuperclass();
                }
                return null;
            } catch (RuntimeException ex) {throw ex;} catch (Exception ex) {return null;}
        }

        public void f(Object object) {
            Method downPolymorphic = getPolymorphicMethod(object);
            if (downPolymorphic == null) {
                System.out.println("Top.f(Object)");
            } else {
                try {
                    downPolymorphic.invoke(this, new Object[]{object});
                } catch (RuntimeException ex) {throw ex;} catch (Exception ex) {
                    throw new RuntimeException(ex.toString());
                }
            }
        }
    }

    public static class Middle extends Top {
        public void f(String s) {
            System.out.println("Middle.f(String)");
        }
    }

    public static class Bottom extends Middle {
        public void f(Integer i) {
            System.out.println("Bottom.f(Integer)");
        }
    }

    public static class RockBottom extends Bottom {
        public void f(String s) {
            System.out.println("RockBottom.f(String)");
        }
    }

    public static void main(String[] args) {
        Top top = new RockBottom();
        top.f(new java.util.Vector());
        top.f("hello");
        top.f(new Integer(42));
        top = new Bottom();
        top.f(new java.util.Vector());
        top.f("hello");
        top.f(new Integer(42));
    }
}
