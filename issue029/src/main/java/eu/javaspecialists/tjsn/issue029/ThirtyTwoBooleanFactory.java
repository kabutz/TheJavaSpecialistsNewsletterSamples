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

package eu.javaspecialists.tjsn.issue029;

public class ThirtyTwoBooleanFactory implements ObjectFactory {
    private static class ThirtyTwoBooleans {
        boolean a0, a1, a2, a3, a4, a5, a6, a7;
        boolean b0, b1, b2, b3, b4, b5, b6, b7;
        boolean c0, c1, c2, c3, c4, c5, c6, c7;
        boolean d0, d1, d2, d3, d4, d5, d6, d7;
    }

    public Object makeObject() {
        return new ThirtyTwoBooleans();
    }
}
