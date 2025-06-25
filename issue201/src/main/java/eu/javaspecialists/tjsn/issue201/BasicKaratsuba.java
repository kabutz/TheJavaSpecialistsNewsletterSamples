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

import static eu.javaspecialists.tjsn.issue201.BigIntegerUtils.*;

public class BasicKaratsuba implements Karatsuba {
    public static final String THRESHOLD_PROPERTY_NAME =
            "eu.javaspecialists.tjsn.math.numbers.BasicKaratsubaThreshold";
    private static final int THRESHOLD = Integer.getInteger(
            THRESHOLD_PROPERTY_NAME, 1000);

    public BigInteger multiply(BigInteger x, BigInteger y) {
        int m = java.lang.Math.min(x.bitLength(), y.bitLength()) / 2;
        if (m <= THRESHOLD)
            return x.multiply(y);

        // x = x1 * 2^m + x0
        // y = y1 * 2^m + y0
        BigInteger[] xs = BigIntegerUtils.split(x, m);
        BigInteger[] ys = BigIntegerUtils.split(y, m);

        // xy = (x1*2^m + x0)(y1*2^m + y0) = z2*2^2m + z1*2^m + z0
        // where:
        // z2 = x1 * y1
        // z0 = x0 * y0
        // z1 = x1 * y0 + x0 * y1 = (x1 + x0)(y1 + y0) - z2 - z0
        BigInteger z2 = multiply(xs[0], ys[0]);
        BigInteger z0 = multiply(xs[1], ys[1]);
        BigInteger z1 = multiply(add(xs), add(ys)).
                subtract(z2).subtract(z0);

        // result = z2 * 2^2m + z1 * 2^m + z0
        return z2.shiftLeft(2 * m).add(z1.shiftLeft(m)).add(z0);
    }
}
