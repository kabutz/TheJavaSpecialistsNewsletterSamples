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

package eu.javaspecialists.tjsn.issue262;

import java.util.*;

public class BruteForceRecursive extends BruteForceBase {
    public static void main(String... args) {
        byte[] is = new byte[7];
        Arrays.fill(is, (byte) '!');
        check(0, 0, is, 0);
    }

    static void check(int depth, int h, byte[] is, int target) {
        if (depth == 7) {
            if (h == target) {
                System.out.println(new String(is));
            }
            return;
        }

        for (byte i : alphabet) {
            is[depth] = i;
            check(depth + 1, h * 31 + i, is, target);
        }
    }
}
