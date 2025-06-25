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

package eu.javaspecialists.tjsn.issue049;

public class BadTest {
    public BadTest(int i) {
    }

    /**
     * @param someone means nothing
     * @return always true
     * @throws bla if something bad happens
     */
    public BadTest() {
    }

    /**
     * @return nothing at all!
     * @return nothing at all!
     * @throws Exception if nothing happens
     * @throws Exception if something happens
     */
    public void method1() throws NullPointerException, Exception {
    }

    private boolean bad;
}
