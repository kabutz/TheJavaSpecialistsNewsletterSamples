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

package eu.javaspecialists.tjsn.issue071;

public class OverloadingTest {
    public abstract static class Top {
        public String f(Object o) {
            String whoAmI = "Top.f(Object)";
            System.out.println(whoAmI);
            return whoAmI;
        }
    }

    public static class Sub extends Top {
        public String f(String s) {
            String whoAmI = "Middle.f(String)";
            System.out.println(whoAmI);
            return whoAmI;
        }
    }

    public static void main(String[] args) {
        Sub sub = new Sub();
        Top top = sub;

        String stringAsString = "someString";
        Object stringAsObject = stringAsString;

        if (top.f(stringAsObject) == sub.f(stringAsString))
        //if (top.f(stringAsObject) == sub.f(stringAsObject))
        //if (top.f(stringAsString) == sub.f(stringAsString))
        //if (top.f(stringAsString) == sub.f(stringAsObject))
        //if (sub.f(stringAsString) == sub.f(stringAsObject))
        //if (top.f(stringAsString) == top.f(stringAsObject))
        {
            System.out.println("Hey, life is great!");
        } else {
            System.out.println("Oh no!");
        }
    }
}
