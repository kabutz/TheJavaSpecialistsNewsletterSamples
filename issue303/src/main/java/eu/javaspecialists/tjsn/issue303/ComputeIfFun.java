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

package eu.javaspecialists.tjsn.issue303;

import java.util.*;

public class ComputeIfFun {
    public static void main(String... args) {
        Map<Integer, Long> map = HashMap.newHashMap(1);
        System.out.println("map = " + map); // {}
        map.computeIfAbsent(-1, key -> {
            System.out.println("computing the value");
            return null; // this will not add an entry into the map
        });
        System.out.println("map = " + map); // {}
        map.computeIfAbsent(-1, key -> {
            System.out.println("computing the value");
            return 0L; // the map now contains -1=0L
        });
        System.out.println("map = " + map); // {-1=0L}
        map.computeIfPresent(-1, (key, value) -> {
            System.out.println("computing the value again");
            return null; // this removes the entry from the map
        });
        System.out.println("map = " + map); // {}
        map.put(-1, null);
        System.out.println("map = " + map); // {-1=null}
        map.compute(-1, (key, value) -> null); // removes the entry
        System.out.println("map = " + map);
    }
}
