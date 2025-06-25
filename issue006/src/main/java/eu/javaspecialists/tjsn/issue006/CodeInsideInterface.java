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

package eu.javaspecialists.tjsn.issue006;

/**
 * This interface contains an interfaces representing a Method and an
 * interface showing the Result of the method call using the callback
 * pattern.  It also contains a data member (automatically public static
 * final) which is an anonymous inner class implementing our Method
 * interface.
 */
public interface CodeInsideInterface {
    public interface Method {
        public void run(Result callback);
    }

    public interface Result {
        public void result(Object answer);

        public void exception(Exception problem);
    }

    Method calculateIQ = new Method() {
        // I always write my data members as final if possible, this catches a
        // lot of bugs at compile time
        private final java.io.BufferedReader stdin = new java.io.BufferedReader(
                new java.io.InputStreamReader(System.in));

        public void run(Result callback) {
            int iq = 100;
            try {
                System.out.print("Do you know Java (y/n)? ");
                if ("y".equals(stdin.readLine())) iq += 20;
                System.out.print("Do you know QuickBasic (y/n)? ");
                if ("y".equals(stdin.readLine())) iq += 20;
                System.out.print("Do you use the Basic GOTO statement (y/n)? ");
                if ("y".equals(stdin.readLine())) iq -= 30;
                System.out.print("Do you frequently use Java reflection (y/n)? ");
                if ("y".equals(stdin.readLine())) iq -= 50;
                callback.result(new Integer(iq));
            } catch (java.io.IOException ex) {
                callback.exception(ex);
            }
        }
    };
}