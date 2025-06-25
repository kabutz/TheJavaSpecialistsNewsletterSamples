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

package eu.javaspecialists.tjsn.issue129;

import com.jamonapi.*;

public class FastExceptionTest {
    public static final int COUNT = 1000 * 1000;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            test(new SlowExceptionThrower());
            test(new FastExceptionThrower());
            test(new SuperFastExceptionThrower());
        }
    }

    private static void test(Thrower t) {
        Monitor mon = MonitorFactory.start(t.getClass().getName());
        for (int i = 0; i < COUNT; i++) {
            try {
                t.causeException();
            } catch (Exception ex) {}
        }
        mon.stop();
        System.out.println(mon);
    }
}
