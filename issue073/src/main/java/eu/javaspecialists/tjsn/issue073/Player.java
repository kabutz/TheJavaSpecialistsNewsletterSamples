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

package eu.javaspecialists.tjsn.issue073;

import java.text.*;
import java.util.*;

/**
 * Super athlete who earns money running around and styling
 * his hair nicely.
 */
public class Player {
    private final Key key;
    private final String name;
    private final Date dateOfBirth;

    public Player(String id, String name, Date dob) {
        this.key = new Key(id);
        this.name = name;
        this.dateOfBirth = dob;
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    private static final DateFormat df =
            new SimpleDateFormat("yyyy/MM/dd");

    public String toString() {
        return name + " born on " + df.format(dateOfBirth);
    }

    public static final class Key {
        private final String id;

        public Key(String id) {
            if (id == null) {
                throw new IllegalArgumentException();
            }
            this.id = id;
        }

        public int hashCode() {
            return id.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) return false;
            return id.equals(((Key) obj).id);
        }
    }
}