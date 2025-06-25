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

package eu.javaspecialists.tjsn.issue131;

import java.util.*;

public class Conference {
    private Collection delegates = new ArrayList();

    public void add(String... names) {
        Collections.addAll(delegates, names);
    }

    public void removeFirst() {
        delegates.remove(0);
    }

    public String toString() {
        return "Conference " + delegates;
    }

    public static void main(String... args) {
        Conference sun_tech_days = new Conference();
        sun_tech_days.add("Herman", "Bobby", "Robert");
        sun_tech_days.removeFirst();
        System.out.println(sun_tech_days);
    }
}
