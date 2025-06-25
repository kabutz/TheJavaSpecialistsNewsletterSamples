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

public abstract class Fibonacci {
    private final FibonacciCache cache;

    protected Fibonacci(FibonacciCache cache) {
        this.cache = cache;
    }

    public Fibonacci() {
        this(null);
    }

    public BigInteger calculate(int n)
            throws InterruptedException {
        if (cache == null) return doActualCalculate(n);

        BigInteger result = cache.get(n);
        if (result == null) {
            cache.put(n, result = doActualCalculate(n));
        }
        return result;
    }

    protected abstract BigInteger doActualCalculate(int n)
            throws InterruptedException;
}
