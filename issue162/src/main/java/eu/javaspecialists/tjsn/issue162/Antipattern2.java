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

package eu.javaspecialists.tjsn.issue162;

public class Antipattern2 {
    private final int val1;
    private final String val2;
    private final long val3;

    public Antipattern2(int val1, String val2, long val3) {
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        try {
            Antipattern2 that = (Antipattern2) o;
            return val1 == that.val1
                    && val3 == that.val3
                    && (val2 == that.val2 || val2.equals(that.val2));
        } catch (ClassCastException ex) {
            return false;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public int hashCode() {
        int result;
        result = val1;
        result = 31 * result + val2.hashCode();
        result = 31 * result + (int) (val3 ^ (val3 >>> 32));
        return result;
    }
}