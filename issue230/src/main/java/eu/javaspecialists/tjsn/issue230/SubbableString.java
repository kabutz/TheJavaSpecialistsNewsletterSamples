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

package eu.javaspecialists.tjsn.issue230;

//@NotThreadSafe
public class SubbableString implements CharSequence {
    private final char[] value;
    private final int offset;
    private final int count;

    public SubbableString(char[] value) {
        this(value, 0, value.length);
    }

    private SubbableString(char[] value, int offset, int count) {
        this.value = value;
        this.offset = offset;
        this.count = count;
    }

    public int length() {
        return count;
    }

    public String toString() {
        return new String(value, offset, count);
    }

    public char charAt(int index) {
        if (index < 0 || index >= count)
            throw new StringIndexOutOfBoundsException(index);
        return value[index + offset];
    }

    public CharSequence subSequence(int start, int end) {
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        }
        if (end > count) {
            throw new StringIndexOutOfBoundsException(end);
        }
        if (start > end) {
            throw new StringIndexOutOfBoundsException(end - start);
        }
        return (start == 0 && end == count) ? this :
                new SubbableString(value, offset + start, end - start);
    }
}
