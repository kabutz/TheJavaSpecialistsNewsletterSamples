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

package eu.javaspecialists.tjsn.issue175;

public class CreationTest {
    public static void main(String... args) {
        // Creating MySerializable by calling NotSerializable no-args
        // constructor, but not the MySerializable constructor.
        MySerializable ms = SilentObjectCreator.create(
                MySerializable.class, NotSerializable.class);
        System.out.println("ms = " + ms);

        // Creating MySerializable by not calling any constructors.
        MySerializable ms2 = SilentObjectCreator.create(
                MySerializable.class
        );
        System.out.println("ms2 = " + ms2);
    }
}
