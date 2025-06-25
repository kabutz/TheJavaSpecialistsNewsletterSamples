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

package eu.javaspecialists.tjsn.issue067;

public class Basic {
    // GotoException is thrown by GOTO to avoid having to call
    // "break" after the call to GOTO in the switch statement.
    // I warned you before that Exceptions were dangerous ;-)
    protected static final class GotoException
            extends RuntimeException {
        private GotoException() {
        }
    }

    private static final GotoException gotoEx =
            new GotoException();

    public static int jump = 10;

    public static void GOTO(int line) {
        jump = line;
        throw gotoEx;
    }

    // STOP changes the current line to be -1, which ends the
    // program
    public static void STOP() {
        GOTO(-1);
    }

    public static void PRINT(String s) {
        System.out.println(s);
    }

    public static void PRINT(int i) {
        System.out.println(i);
    }

    public static int[] DIM(int n) {
        return new int[n];
    }

    public static double RND() {
        return Math.random();
    }

    public static int INT(double d) {
        return (int) d;
    }
}
