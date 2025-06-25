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

public class FibonacciFormulaLong extends NonCachingFibonacci {
    private static final double root5 = Math.sqrt(5);
    private static final double PHI = (1 + root5) / 2;
    private static final double PSI = (1 - root5) / 2;
    private static final int MAXIMUM_PRECISE_NUMBER = 71;

    public BigInteger calculate(int n)
            throws InterruptedException {
        if (Thread.interrupted()) throw new InterruptedException();
        if (n < 0) throw new IllegalArgumentException();
        if (n > MAXIMUM_PRECISE_NUMBER)
            throw new IllegalArgumentException(
                    "Precision loss after " + MAXIMUM_PRECISE_NUMBER);
        return new BigInteger(Long.toString(fibWithFormula(n)));
    }

    private static long fibWithFormula(int n) {
        return (long) ((Math.pow(PHI, n) - Math.pow(PSI, n)) / root5);
    }
}
