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

package eu.javaspecialists.tjsn.issue312;

public class PrintingNumbersWithHash {
    public static void main(String... args) {
        System.out.format("0%o%n", 01234);
        System.out.format("%#o%n", 01234);
        System.out.format("%#x%n", 0xcafebabe);
        System.out.format("%#X%n", 0xcafebabe);
        System.out.format("0x%x%n", 0xcafebabe);
        System.out.format("0x%X%n", 0xcafebabe);
    }
}
