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

package eu.javaspecialists.tjsn.issue068;

public class StringAppendDiff {
    public static void main(String[] args) {
        System.out.println("String += 10000 additions");
        Timer.time(new Runnable() {
            public void run() {
                String s = "";
                for (int i = 0; i < 10000; i++) {
                    s += i;
                }
                // we have to use "s" in some way, otherwise a clever
                // compiler would optimise it away.  Not that I have
                // any such compiler, but just in case ;-)
                System.out.println("Length = " + s.length());
            }
        });

        System.out.println(
                "StringBuffer 300 * 10000 additions initial size wrong");
        Timer.time(new Runnable() {
            public void run() {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < (300 * 10000); i++) {
                    sb.append(i);
                }
                String s = sb.toString();
                System.out.println("Length = " + s.length());
            }
        });

        System.out.println(
                "StringBuffer 300 * 10000 additions initial size right");
        Timer.time(new Runnable() {
            public void run() {
                StringBuffer sb = new StringBuffer(19888890);
                for (int i = 0; i < (300 * 10000); i++) {
                    sb.append(i);
                }
                String s = sb.toString();
                System.out.println("Length = " + s.length());
            }
        });
    }
}
