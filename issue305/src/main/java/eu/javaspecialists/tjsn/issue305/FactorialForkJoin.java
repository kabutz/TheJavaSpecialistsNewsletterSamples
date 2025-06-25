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

package eu.javaspecialists.tjsn.issue305;

import java.math.*;
import java.util.concurrent.*;
import java.util.function.*;

public class FactorialForkJoin extends AbstractFactorial {
    public FactorialForkJoin(
            BinaryOperator<BigInteger> multiply) {
        super(multiply);
    }

    @Override
    public BigInteger calculate(int n) {
        if (n < 0) throw new IllegalArgumentException("n < 0");
        if (n == 0) return BigInteger.ONE;
        return new FactorialTask(1, n).invoke();
    }

    private class FactorialTask extends RecursiveTask<BigInteger> {
        private final int from, to;

        public FactorialTask(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected BigInteger compute() {
            if (from == to) return BigInteger.valueOf(from);
            int mid = (from + to) >>> 1;
            var leftTask = new FactorialTask(from, mid);
            var rightTask = new FactorialTask(mid + 1, to);
            leftTask.fork();
            BigInteger right = rightTask.invoke();
            BigInteger left = leftTask.join();
            return multiply(left, right);
        }
    }
}
