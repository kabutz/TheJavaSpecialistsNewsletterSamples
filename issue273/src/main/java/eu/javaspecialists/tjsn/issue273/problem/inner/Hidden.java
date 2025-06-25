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

package eu.javaspecialists.tjsn.issue273.problem.inner;

import eu.javaspecialists.tjsn.issue273.problem.*;

public class Hidden {
    public static A getPrivateInnerClass() {
        return new C();
    }

    private static class C implements A, B {
        public String foo() {
            return "Hello World";
        }

        public String bar() {
            return "Should not be visible";
        }
    }

    public static A getMethodClass() {
        class D implements A {
            public CharSequence foo() {
                return "inside method";
            }
        }
        return new D();
    }

    public static A getLambda() {
        return () -> "Hello Lambert";
    }
}
