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

package eu.javaspecialists.tjsn.issue157;

public class PolymorphismTest {
    private static final int UPTO = 1000 * 1000 * 1000;

    public static void main(String... args) {
        System.out.println("Empty\tA1\tA2\tA3\tA3/C+D\tA4");
        for (int j = 0; j < 10; j++) {

            long time = System.currentTimeMillis();
            for (int i = 0; i < UPTO; i++) {
                //empty loop
            }
            time = System.currentTimeMillis() - time;
            System.out.print(time + "\t");
            System.out.flush();

            A1 a = new A1(new B1());
            time = System.currentTimeMillis();
            for (int i = 0; i < UPTO; i++) {
                a.run();
            }
            time = System.currentTimeMillis() - time;
            System.out.print(time + "\t");
            System.out.flush();

            A2 a2 = new A2(new C2());
            time = System.currentTimeMillis();
            for (int i = 0; i < UPTO; i++) {
                a2.run();
            }
            time = System.currentTimeMillis() - time;
            System.out.print(time + "\t");
            System.out.flush();

            A3 a3 = new A3(new C3());
            time = System.currentTimeMillis();
            for (int i = 0; i < UPTO; i++) {
                a3.run();
            }
            time = System.currentTimeMillis() - time;
            System.out.print(time + "\t");
            System.out.flush();

            A3 a3_c3 = new A3(new C3());
            A3 a3_d3 = new A3(new D3());
            time = System.currentTimeMillis();
            for (int i = 0, upto = UPTO / 2; i < upto; i++) {
                a3_c3.run();
                a3_d3.run();
            }
            time = System.currentTimeMillis() - time;
            System.out.print(time + "\t");
            System.out.flush();

            A4 a4_c4 = new A4(new C4());
            A4 a4_d4 = new A4(new D4());
            A4 a4_e4 = new A4(new E4());
            A4 a4_f4 = new A4(new F4());
            time = System.currentTimeMillis();
            for (int i = 0, upto = UPTO / 4; i < upto; i++) {
                a4_c4.run();
                a4_d4.run();
                a4_e4.run();
                a4_f4.run();
            }
            time = System.currentTimeMillis() - time;
            System.out.println(time);
        }
    }
}
