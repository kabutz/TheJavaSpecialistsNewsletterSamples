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

package eu.javaspecialists.tjsn.issue201;

import java.math.*;

public class FibonacciRecursiveDijkstra extends Fibonacci {
    public BigInteger doActualCalculate(int n)
            throws InterruptedException {
        if (Thread.interrupted()) throw new InterruptedException();
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;
        if (n % 2 == 1) {
            // f(2n-1) = f(n-1)^2 + f(n)^2
            int left = (n + 1) / 2;
            int right = (n + 1) / 2 - 1;
            return square(calculate(left)).add(square(calculate(right)));
        } else {
            // f(2n) = (2 * f(n-1) + f(n)) * f(n)
            int n_ = n / 2;
            BigInteger fn = calculate(n_);
            BigInteger fn_1 = calculate(n_ - 1);
            return (fn_1.add(fn_1).add(fn)).multiply(fn);
        }
    }

    protected BigInteger multiply(BigInteger bi0, BigInteger bi1) {
        return bi0.multiply(bi1);
    }

    protected BigInteger square(BigInteger num) {
        return multiply(num, num);
    }
}
