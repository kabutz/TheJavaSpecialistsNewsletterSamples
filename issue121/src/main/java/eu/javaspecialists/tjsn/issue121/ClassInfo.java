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

package eu.javaspecialists.tjsn.issue121;

public class ClassInfo implements Comparable<ClassInfo> {
    private final String className;
    private final int depth;

    public ClassInfo(String className, int depth) {
        this.className = className;
        this.depth = depth;
    }

    public int compareTo(ClassInfo o) {
        if (depth < o.depth) return -1;
        if (depth > o.depth) return 1;
        return className.compareTo(o.className);
    }

    public String toString() {
        return depth + "\t" + className;
    }
}



