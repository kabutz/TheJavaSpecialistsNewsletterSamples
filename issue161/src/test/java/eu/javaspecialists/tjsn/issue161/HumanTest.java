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

package eu.javaspecialists.tjsn.issue161;

import junit.framework.*;

import java.util.*;

public class HumanTest extends TestCase {
    public void testSingingAddingEnum() {
        EnumBuster<HumanState> buster =
                new EnumBuster<HumanState>(HumanState.class,
                        Human.class);

        try {
            Human heinz = new Human();
            heinz.sing(HumanState.HAPPY);
            heinz.sing(HumanState.SAD);

            HumanState MELLOW = buster.make("MELLOW");
            buster.addByValue(MELLOW);
            System.out.println(Arrays.toString(HumanState.values()));

            try {
                heinz.sing(MELLOW);
                fail("Should have caused an IllegalStateException");
            } catch (IllegalStateException success) {}
        } finally {
            System.out.println("Restoring HumanState");
            buster.restore();
            System.out.println(Arrays.toString(HumanState.values()));
        }
    }
}
