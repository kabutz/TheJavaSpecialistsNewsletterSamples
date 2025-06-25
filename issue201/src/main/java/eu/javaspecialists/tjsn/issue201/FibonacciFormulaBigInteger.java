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

public class FibonacciFormulaBigInteger extends NonCachingFibonacci {
    private static final BigDecimal root5 = new BigDecimal(
            "2.23606797749978969640917366873127623544061835961152572" +
                    "4270897245410520925637804899414414408378782274969508176" +
                    "1507737835042532677244470738635863601215334527088667781" +
                    "7319187916581127664532263985658053576135041753378");
    private static final BigDecimal PHI = root5.add(
            new BigDecimal(1)).divide(new BigDecimal(2));
    private static final BigDecimal PSI = root5.subtract(
            new BigDecimal(1)).divide(new BigDecimal(2));
    private static final int MAXIMUM_PRECISE_NUMBER = 1000;

    public BigInteger calculate(int n) throws InterruptedException {
        if (Thread.interrupted()) throw new InterruptedException();
        if (n < 0) throw new IllegalArgumentException();
        if (n > MAXIMUM_PRECISE_NUMBER)
            throw new IllegalArgumentException(
                    "Precision loss after " + MAXIMUM_PRECISE_NUMBER);

        BigDecimal phiToTheN = PHI.pow(n);
        if (Thread.interrupted()) throw new InterruptedException();
        BigDecimal psiToTheN = PSI.pow(n);
        if (Thread.interrupted()) throw new InterruptedException();
        BigDecimal phiMinusPsi = phiToTheN.subtract(psiToTheN);
        BigDecimal result = phiMinusPsi.divide(
                root5, 0, RoundingMode.UP);
        return result.toBigInteger();
    }
}
