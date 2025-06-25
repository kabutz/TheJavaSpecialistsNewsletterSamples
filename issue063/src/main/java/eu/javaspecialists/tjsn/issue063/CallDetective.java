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

package eu.javaspecialists.tjsn.issue063;

/**
 * This interface is used to determine who called us.
 * The implementation does not have to be thread-safe.
 */
public interface CallDetective {
    /**
     * Returns a String representation of who called us,
     * going back depth levels.
     *
     * @param depth must be greater than 0 and may not
     *              exceed the call stack depth.
     */
    public String findCaller(int depth);

    public class Factory {
        public static CallDetective makeCallDetective() {
            if ("1.4".compareTo(System.getProperty("java.version")) > 0) {
                return new CallDetective1_3();
            } else {
                return new CallDetective1_4();
            }
        }
    }
}
