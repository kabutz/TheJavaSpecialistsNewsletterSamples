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

package eu.javaspecialists.tjsn.issue236;

public class BigOComparison {
    public static void main(String... args) {
        for (int n = 1; n <= 1_000_000_000; n *= 10) {
            double n_2 = Math.pow(n, 2);
            double n_1_585 = Math.pow(n, 1.585);
            double n_1_465 = Math.pow(n, 1.465);

            double karatsuba_faster_than_quadratic = n_2 / n_1_585;
            double toom_cook_faster_than_karatsuba = n_1_585 / n_1_465;

            System.out.printf("%d\t%.2fx\t%.2fx%n",
                    n, karatsuba_faster_than_quadratic,
                    toom_cook_faster_than_karatsuba);
        }
    }
}
