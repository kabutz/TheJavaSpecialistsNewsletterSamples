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

package eu.javaspecialists.tjsn.issue216;

import javax.xml.transform.*;
import java.math.*;
import java.util.*;

public class ContinuedFractionTest {
  public static void main(String... args) {
    ContinuedFraction cf1 = // 2.3456
        new ContinuedFraction(2, 2, 1, 8, 2, 1, 1, 4);
    System.out.println(cf1.doubleValue());
    System.out.println(cf1.inverse().doubleValue());
    System.out.println(1.0 / 2.3456);


    int[] ones = new int[250];
    Arrays.fill(ones, 1);
    ContinuedFraction phi = new ContinuedFraction(1, ones);
    System.out.println(phi.calculate(100));
    System.out.println("1.6180339887498948482045868343656381" +
        "177203091798057628621354486227052604628189024497072" +
        "07204189391137484754");

    ContinuedFraction pi = new ContinuedFraction(
        3, 7, 15, 1, 292, 1
    );
    System.out.println(pi.calculate(9));
    System.out.println("3.141592653589");
    System.out.println(Math.PI);
  }
}
