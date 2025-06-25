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

import java.math.*;

public class Fibonacci {
    public BigInteger f(int n) {
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;
        if (n % 2 == 1) { // F(2n-1) = F(n-1)^2 + F(n)^2
            n = (n + 1) / 2;
            BigInteger fn_1 = f(n - 1);
            BigInteger fn = f(n);
            return fn_1.multiply(fn_1).add(fn.multiply(fn));
        } else { // F(2n) = ( 2 F(n-1) + F(n) ) F(n)
            n = n / 2;
            BigInteger fn_1 = f(n - 1);
            BigInteger fn = f(n);
            return fn_1.shiftLeft(1).add(fn).multiply(fn);
        }
    }

    public BigInteger f_slow(int n) {
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;
        return f_slow(n - 1).add(f_slow(n - 2));
    }
}
