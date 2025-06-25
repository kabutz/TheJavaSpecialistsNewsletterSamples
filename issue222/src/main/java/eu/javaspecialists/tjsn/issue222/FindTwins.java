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

package eu.javaspecialists.tjsn.issue222;

public class FindTwins {
    public static void main(String... args) {
        Object obj = new Object();
        Object twin = findTwin(obj);
        System.out.printf("found twin: %s and %s, but == is %b%n",
                obj, twin, obj == twin);
    }

    private static Object findTwin(Object obj) {
        int hash = obj.hashCode();
        Object twin;
        long created = 0;
        do {
            twin = new Object();
            if ((++created & 0xfffffff) == 0) {
                System.out.printf("%,d created%n", created);
            }
        } while (twin.hashCode() != obj.hashCode());
        System.out.printf("We had to create %,d objects%n", created);
        return twin;
    }
}
