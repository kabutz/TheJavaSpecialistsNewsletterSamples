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

package eu.javaspecialists.tjsn.issue272;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.fail;

// Run with --add-opens java.base/java.lang=ALL-UNNAMED
public class EnumSwitchTest {
    @Test
    public void testSingingDeletingEnum()
            throws ReflectiveOperationException {
        EnumBuster<HumanState> buster = new EnumBuster<>(
                HumanState.class, EnumSwitchTest.class);
        try {
            for (HumanState state : HumanState.values()) {
                switch (state) {
                    case HAPPY:
                    case SAD:
                        break;
                    default:
                        fail("Unknown state");
                }
            }

            buster.deleteByValue(HumanState.HAPPY);
            for (HumanState state : HumanState.values()) {
                switch (state) {
                    case SAD:
                        break;
                    case HAPPY:
                    default:
                        fail("Unknown state");
                }
            }

            buster.undo();
            buster.deleteByValue(HumanState.SAD);
            for (HumanState state : HumanState.values()) {
                switch (state) {
                    case HAPPY:
                        break;
                    case SAD:
                    default:
                        fail("Unknown state");
                }
            }

            buster.deleteByValue(HumanState.HAPPY);
            for (HumanState state : HumanState.values()) {
                switch (state) {
                    case HAPPY:
                    case SAD:
                    default:
                        fail("Unknown state");
                }
            }
        } finally {
            buster.restore();
        }
    }
}
