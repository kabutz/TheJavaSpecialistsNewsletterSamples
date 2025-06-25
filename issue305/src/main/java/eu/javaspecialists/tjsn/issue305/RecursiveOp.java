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

@SuppressWarnings("serial")
abstract sealed class RecursiveOp
        extends RecursiveTask<BigInteger> {
    /**
     * The threshold until when we should continue forking
     * recursive ops if parallel is true. This threshold is only
     * relevant for Toom Cook 3 multiply and square.
     */
    private static final int PARALLEL_FORK_DEPTH_THRESHOLD =
            calculateMaximumDepth(ForkJoinPool.getCommonPoolParallelism());

    private static final int calculateMaximumDepth(int parallelism) {
        return 32 - Integer.numberOfLeadingZeros(parallelism);
    }

    final boolean parallel;
    /**
     * The current recursing depth. Since it is a logarithmic
     * algorithm, we do not need an int to hold the number.
     */
    final byte depth;

    private RecursiveOp(boolean parallel, int depth) {
        this.parallel = parallel;
        this.depth = (byte) depth;
    }

    private static int getParallelForkDepthThreshold() {
        if (Thread.currentThread() instanceof ForkJoinWorkerThread fjwt) {
            return calculateMaximumDepth(fjwt.getPool().getParallelism());
        } else {
            return PARALLEL_FORK_DEPTH_THRESHOLD;
        }
    }

    protected RecursiveTask<BigInteger> forkOrInvoke() {
        if (parallel && depth <= getParallelForkDepthThreshold()) fork();
        else invoke();
        return this;
    }

    @SuppressWarnings("serial")
    private static final class RecursiveMultiply extends RecursiveOp {
        private final BigInteger a;
        private final BigInteger b;

        public RecursiveMultiply(BigInteger a, BigInteger b,
                                 boolean parallel, int depth) {
            super(parallel, depth);
            this.a = a;
            this.b = b;
        }

        @Override
        public BigInteger compute() {
            return a.multiply(b, true, parallel, depth);
        }
    }

    @SuppressWarnings("serial")
    private static final class RecursiveSquare extends RecursiveOp {
        private final BigInteger a;

        public RecursiveSquare(BigInteger a,
                               boolean parallel, int depth) {
            super(parallel, depth);
            this.a = a;
        }

        @Override
        public BigInteger compute() {
            return a.square(true, parallel, depth);
        }
    }

    private static RecursiveTask<BigInteger> multiply(
            BigInteger a, BigInteger b, boolean parallel, int depth) {
        return new RecursiveMultiply(a, b, parallel, depth).forkOrInvoke();
    }

    private static RecursiveTask<BigInteger> square(
            BigInteger a, boolean parallel, int depth) {
        return new RecursiveSquare(a, parallel, depth).forkOrInvoke();
    }
}
