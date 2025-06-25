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
