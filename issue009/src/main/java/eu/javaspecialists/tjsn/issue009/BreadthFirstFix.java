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

public class BreadthFirstFix {
    public static class Top {
        public void f(Object o) {
            System.out.println("Top.f(Object)");
        }
    }

    public static class Middle extends Top {
        public void f(Object o) {
            if (o instanceof String)
                f((String) o);
            else
                super.f(o);
        }

        public void f(String s) {
            System.out.println("Middle.f(String)");
        }
    }

    public static void main(String[] args) {
        Top top = new Middle();
        top.f(new java.util.Vector());
        top.f("hello");
        top.f((Object) "bye");
    }
}
